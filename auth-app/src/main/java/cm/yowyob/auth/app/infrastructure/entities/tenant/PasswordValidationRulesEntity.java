package cm.yowyob.auth.app.infrastructure.entities.tenant;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PasswordValidationRulesEntity {

    private Integer maxLength;
    private Integer minLength;
    private Boolean rememberPreviousPasswordEnabled;
    private Integer rememberPreviousPasswordCount;
    private Boolean requireMixedCase;
    private Boolean requireNonAlpha;
    private Boolean requireNumber;
    private Boolean validateOnLogin;

}
