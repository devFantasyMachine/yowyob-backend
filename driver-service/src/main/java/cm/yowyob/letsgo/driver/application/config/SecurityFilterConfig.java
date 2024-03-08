package cm.yowyob.letsgo.driver.application.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;



@Slf4j
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityFilterConfig {


    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }


    @Value("${letsgo.auth.api.allowedOrigins}")
    private String apiAllowedOrigins;



    // FIXME: 19/08/2023
    @Bean
    UserDetailsManager userDetailsManager() {

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.builder()
                        .username("SYSTEM")
                        .roles("SYSTEM")
                        .password("{noop}SYSTEM")
                .build());

        return userDetailsManager;
    }


    @Bean
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter());


        httpSecurity
                .formLogin().disable()
                .httpBasic().and() // for system authentication
                .logout().disable()
                .csrf().disable()
                .cors()
                .configurationSource(apiCorsConfigurationSource())
                .and()
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/api/v0/admins").hasAnyAuthority("ADMIN")
                                .requestMatchers("/actuator").hasAnyAuthority("SYSTEM")

                                //.requestMatchers("/api/v0/drivers/**").hasAuthority("DRIVER")
                                .requestMatchers("/api/v0/drivers/**").hasAuthority("LETSGO_USER")
                                .requestMatchers("/api/v0/drivers/infos").permitAll()

                                .requestMatchers("/api/v0/claims/**").hasAuthority("LETSGO_USER")
                                .requestMatchers("/api/v0/plannings/**").hasAuthority("LETSGO_USER")
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


    public CorsConfigurationSource apiCorsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));

        config.addAllowedOrigin(apiAllowedOrigins);

        source.registerCorsConfiguration("/api/**", config);
        return source;
    }


    @SneakyThrows
    private void apiAccessDenied(HttpServletRequest request, HttpServletResponse response, Exception authException) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        log.error("ACCESS_DENIED", authException);

        response.getWriter().write("{ \"error\": \"ACCESS_DENIED\",\"message\": \"Full Authentication is required\"  }");

    }


}
