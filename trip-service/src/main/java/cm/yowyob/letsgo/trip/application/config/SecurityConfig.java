
package cm.yowyob.letsgo.trip.application.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.CookieRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;




@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    boolean securityDebug = true;

    private static final String[] AUTH_WHITELIST = {
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/v2/api-docs/**",
        "/webjars/**"
    };



    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }


    // TODO add letsgo system authentication

    // FIXME: 19/08/2023
    @Bean
    UserDetailsManager userDetailsManager() {

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.builder()
                .username("system")
                .roles("SYSTEM")
                .password("{noop}system")
                .build());

        return userDetailsManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {


        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());


        httpSecurity
                .formLogin().disable()
                .httpBasic().and() // for system authentication
                .logout().disable()
                .csrf().disable()
                .cors()
                .configurationSource(configurationSource())
                .and()
                .requestCache().requestCache(new CookieRequestCache()).and()
                .authorizeHttpRequests(authorizeHttpRequests ->

                        // TODO: 25/08/2023 suppress withe list
                        authorizeHttpRequests
                                .requestMatchers(AUTH_WHITELIST).permitAll() // remember
                                .requestMatchers("/api/v0/admins").hasAnyAuthority("ADMIN")
                                .requestMatchers("/actuator").hasAnyAuthority("SYSTEM")

                                //.requestMatchers("/api/v0/reservations/**").hasAuthority("SYSTEM")
                                .requestMatchers("/api/v0/drafts/**").hasAuthority("LETSGO_USER")

                                .requestMatchers("/api/v0/planner-plans/**").hasAuthority("LETSGO_USER")
                                .requestMatchers("/api/v0/pooler-plans/**").hasAuthority("LETSGO_USER")
                                .anyRequest().authenticated()
                )
                .sessionManagement(config ->
                        config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                .and()
                .accessDeniedHandler(this::apiAccessDenied)
                .authenticationEntryPoint(this::apiAccessDenied)
                .and();

        return httpSecurity.build();


    }



    @SneakyThrows
    private void apiAccessDenied(HttpServletRequest request, HttpServletResponse response, Exception authException) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        log.error("ACCESS_DENIED", authException);

        response.getWriter().write("{ \"error\": \"ACCESS_DENIED\",\"message\": \"Full Authentication is required\"  }");

    }

    
  

    public CorsConfigurationSource configurationSource() {
    
        UrlBasedCorsConfigurationSource source =  new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        
        return source;
    }


}





