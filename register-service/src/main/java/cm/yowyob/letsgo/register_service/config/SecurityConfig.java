package cm.yowyob.letsgo.register_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests()
                    .anyRequest().authenticated()
                .and()
                .httpBasic().and()
                .formLogin().disable()
                .csrf().disable();

        return httpSecurity.build();
    }










}
