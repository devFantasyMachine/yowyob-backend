package cm.yowyob.auth.app.infrastructure.entities.tenant;


import cm.yowyob.auth.app.domain.model.tenant.PasswordValidationRules;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantPasswordConfigEntity {


    private PasswordValidationRulesEntity passwordValidationRules;
    private Boolean maximumPasswordAgeEnabled;
    private Integer maximumPasswordAgeDays;

}
