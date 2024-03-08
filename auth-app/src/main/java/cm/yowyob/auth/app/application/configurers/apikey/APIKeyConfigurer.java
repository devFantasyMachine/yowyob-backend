package cm.yowyob.auth.app.application.configurers.apikey;


import cm.yowyob.auth.app.domain.handlers.APIKeyManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.util.Assert;

import java.util.Objects;


public class APIKeyConfigurer extends AbstractHttpConfigurer<APIKeyConfigurer, HttpSecurity> {

    private APIKeyManager apiKeyManager;

    public APIKeyConfigurer(APIKeyManager apiKeyManager) {
        super();
        this.apiKeyManager = apiKeyManager;
    }

    public HttpSecurity withKeyManager(APIKeyManager apiKeyManager){

        this.apiKeyManager = Objects.requireNonNull(apiKeyManager);
        return this.getBuilder();
    }


    @Override
    public void init(HttpSecurity http) throws Exception {
        super.init(http);
        Assert.notNull(this.apiKeyManager, "authenticationProvider must not be null");
    }


    @Override
    public void configure(HttpSecurity http){
        http.addFilterBefore(new APIKeyAuthenticationFilter(this.apiKeyManager), CsrfFilter.class);
    }



}
