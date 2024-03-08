package cm.yowyob.auth.app.domain.model.tenant;


import cm.yowyob.auth.app.domain.model.captcha.CaptchaMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantCaptchaConfig {

    private Set<CaptchaMethod> captchaMethods;
    private Boolean threshold;
    private Boolean enabled;
}
