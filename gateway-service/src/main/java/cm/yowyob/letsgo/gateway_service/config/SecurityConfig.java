/*
package cm.yowyob.letsgo.gateway_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Bean
    SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeHttpRequests()
                    .requestMatchers("actuator/info", "/health").authenticated()
                    .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                    .disable()
                .csrf()
                    .disable();
                */
/*.oauth2ResourceServer()
                    .jwt()*//*

                //.and();

        return httpSecurity.build();
    }









}
*/
