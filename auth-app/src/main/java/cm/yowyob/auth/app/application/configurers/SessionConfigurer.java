package cm.yowyob.auth.app.application.configurers;

import cm.yowyob.auth.app.application.configurers.apikey.APIKeyConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;







public class SessionConfigurer extends AbstractHttpConfigurer<APIKeyConfigurer, HttpSecurity> {

    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
    }


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
    }


}
