package cm.yowyob.auth.app.domain.model.tenant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantPasswordConfig {

    @Builder.Default
    private PasswordValidationRules passwordValidationRules = new PasswordValidationRules();
    @Builder.Default
    private Boolean maximumPasswordAgeEnabled = false;
    @Builder.Default
    private Integer maximumPasswordAgeDays = 180;

}
