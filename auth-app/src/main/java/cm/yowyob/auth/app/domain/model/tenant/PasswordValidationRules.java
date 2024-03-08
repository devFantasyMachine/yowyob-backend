package cm.yowyob.auth.app.domain.model.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordValidationRules {

    private int maxLength = 256;
    private int minLength = 8;
    private Boolean rememberPreviousPasswordEnabled = false;
    private int rememberPreviousPasswordCount = 1;
    private boolean requireMixedCase;
    private boolean requireNonAlpha;
    private boolean requireNumber;
    private boolean validateOnLogin;

}
