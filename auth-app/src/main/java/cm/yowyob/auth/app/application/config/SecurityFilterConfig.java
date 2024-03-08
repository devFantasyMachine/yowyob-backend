package cm.yowyob.auth.app.application.config;


import cm.yowyob.auth.app.application.configurers.SessionAuthenticationFilter;
import cm.yowyob.auth.app.application.configurers.apikey.APIKeyConfigurer;
import cm.yowyob.auth.app.domain.handlers.APIKeyManager;
import cm.yowyob.auth.app.domain.model.user.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityFilterConfig {

    private static final long MAX_AGE_SECS = 3600;

    @Autowired
    APIKeyManager apiKeyManager;

    @Autowired
    OAuth2AuthorizationService oAuth2AuthorizationService;

    @Autowired
    OAuth2AuthorizationConsentService authorizationConsentService;

    @Autowired
    RegisteredClientRepository registeredClientRepository;

    @Autowired
    AuthorizationServerSettings authorizationServerSettings;

    @Autowired
    OAuth2TokenGenerator<?> tokenGenerator;

    @Autowired
    JwtDecoder jwtDecoder;


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());


        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        httpSecurity.apply(authorizationServerConfigurer);

        authorizationServerConfigurer
                .registeredClientRepository(registeredClientRepository)
                .authorizationService(oAuth2AuthorizationService)
                .authorizationConsentService(authorizationConsentService)
                .authorizationServerSettings(authorizationServerSettings)
                .tokenGenerator(tokenGenerator)
                //.deviceAuthorizationEndpoint(Customizer.withDefaults())
                //.deviceVerificationEndpoint(Customizer.withDefaults())

                .authorizationEndpoint(authorizationEndpoint ->
                        authorizationEndpoint
                                .consentPage("/oauth2/consent")
                                .authorizationResponseHandler(this::sendAuthorizationResponse)
                )
                .oidc(Customizer.withDefaults()); // Enable OpenID Connect 1.0


        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        httpSecurity
                .securityMatcher(endpointsMatcher)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .cors(cors ->
                        cors.configurationSource(apiCorsConfigurationSource())
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(this::apiAccessDenied)
                                .authenticationEntryPoint(this::apiAccessDenied)
                )
                .sessionManagement(config ->
                        config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt(jwtConfigurer ->
                                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                );

        httpSecurity.addFilterBefore(new SessionAuthenticationFilter(jwtDecoder), CsrfFilter.class);

        return httpSecurity.build();
    }




    @Bean
    @Order(1)
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());

        httpSecurity.apply(new APIKeyConfigurer(apiKeyManager));

        httpSecurity
                .securityMatcher("/**")
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors ->
                        cors.configurationSource(apiCorsConfigurationSource())
                )
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.GET, "/invitations/**").permitAll()
                                .requestMatchers("/registrations/**").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/tenants/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/tenants/**")
                                .hasAnyAuthority(Role.ADMIN_ROLE_NAME, Role.DEVELOPER_ROLE_NAME)
                                .requestMatchers(HttpMethod.PUT, "/tenants/**")
                                .hasAnyAuthority(Role.ADMIN_ROLE_NAME, Role.DEVELOPER_ROLE_NAME)
                                .requestMatchers(HttpMethod.DELETE, "/tenants/**")
                                .hasAnyAuthority(Role.ADMIN_ROLE_NAME, Role.DEVELOPER_ROLE_NAME)

                                .requestMatchers(HttpMethod.GET, "/admins/add-admin")
                                .hasAnyAuthority("SCOPE_SUPER_ADMIN", "SUPER_ADMIN")
                                .requestMatchers("/api/v0/managers/**").hasAuthority("SCOPE_MANAGER")
                                .requestMatchers(HttpMethod.POST, "/api/v0/register/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/v0/auth/code/verify").permitAll()
                                .requestMatchers("/userinfo/**").hasAuthority("USER")
                                .requestMatchers("/api/v0/userconfig/**").hasAuthority("USER")
                                .anyRequest().authenticated()

                )
                .sessionManagement(config ->
                        config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(oAuth2ResourceServerConfigurer ->
                        oAuth2ResourceServerConfigurer.jwt(jwtConfigurer ->
                                jwtConfigurer
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                )
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer
                                .accessDeniedHandler(this::apiAccessDenied)
                                .authenticationEntryPoint(this::apiAccessDenied)
                );


        httpSecurity.addFilterAfter(new SessionAuthenticationFilter(jwtDecoder), BearerTokenAuthenticationFilter.class);

        return httpSecurity.build();

    }




    public CorsConfigurationSource apiCorsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        config.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", config);
        config.setMaxAge(MAX_AGE_SECS);
        return source;
    }



    @SneakyThrows
    private void apiAccessDenied(HttpServletRequest request, HttpServletResponse response, Exception authException) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        authException.printStackTrace();
        response.getWriter().write("{ \"error\": \"ACCESS_DENIED\",\"message\": \"" + authException.getMessage() + "\"  }");

    }


    private void sendAuthorizationResponse(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthentication = (OAuth2AuthorizationCodeRequestAuthenticationToken) authentication;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(authorizationCodeRequestAuthentication.getRedirectUri())
                .queryParam("code", new Object[]{authorizationCodeRequestAuthentication.getAuthorizationCode().getTokenValue()});

        if (StringUtils.hasText(authorizationCodeRequestAuthentication.getState())) {
            uriBuilder.queryParam("state", new Object[]{UriUtils.encode(authorizationCodeRequestAuthentication.getState(), StandardCharsets.UTF_8)});
        }

        String redirectUri = uriBuilder.build(true).toUriString();
        response.getWriter().write("{\"redirect_uri\": \"" + redirectUri + "\"  }");

    }


}


