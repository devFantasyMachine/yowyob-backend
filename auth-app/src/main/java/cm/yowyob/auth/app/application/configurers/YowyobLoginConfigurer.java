/*
package cm.yowyob.auth.app.application.configurers;

import cm.yowyob.letsgo.auth.service.application.authentication.YowyobDaoAuthenticationProvider;
import cm.yowyob.letsgo.auth.service.application.authentication.YowyobUserDetailsService;
import cm.yowyob.letsgo.auth.service.application.filters.YowyobAuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;


public class YowyobLoginConfigurer extends AbstractHttpConfigurer<YowyobLoginConfigurer, HttpSecurity> {

    private final YowyobUserDetailsService yowyobUserDetailsService;
    private final YowyobAuthenticationSuccessHandler yowyobAuthenticationSuccessHandler;
    private final PasswordEncoder passwordEncoder;


    public YowyobLoginConfigurer(YowyobAuthenticationSuccessHandler yowyobAuthenticationSuccessHandler, YowyobUserDetailsService yowyobUserDetailsService,  PasswordEncoder passwordEncoder) {
        super();
        this.passwordEncoder = passwordEncoder;
        this.yowyobUserDetailsService = yowyobUserDetailsService;
        this.yowyobAuthenticationSuccessHandler = yowyobAuthenticationSuccessHandler;
    }


    @Override
    public void init(HttpSecurity http) throws Exception {
        super.init(http);
        Assert.notNull(this.yowyobAuthenticationSuccessHandler, "yowyobAuthenticationSuccessHandler must not be null");

        YowyobDaoAuthenticationProvider yowyobDaoAuthenticationProvider =
                new YowyobDaoAuthenticationProvider(yowyobUserDetailsService, passwordEncoder);

        http.authenticationProvider(yowyobDaoAuthenticationProvider);
    }


    @Override
    public void configure(HttpSecurity http) {

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        YowyobAuthenticationFilter yowyobAuthenticationFilter =
                new YowyobAuthenticationFilter(authenticationManager);

         yowyobAuthenticationFilter.withAuthenticationSuccessHandler(yowyobAuthenticationSuccessHandler);

        http.addFilterBefore(yowyobAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }


}
*/
