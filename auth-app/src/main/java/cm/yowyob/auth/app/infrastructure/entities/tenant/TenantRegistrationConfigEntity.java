package cm.yowyob.auth.app.infrastructure.entities.tenant;

import cm.yowyob.auth.app.domain.model.tenant.LoginMethod;
import cm.yowyob.auth.app.domain.model.tenant.TenantRegistrationConfig;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;



@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class TenantRegistrationConfigEntity {


    private Boolean useUsername;
    private Boolean useBirthdate;
    private Boolean useFirstName;
    private Boolean useLastName;
    private Boolean usePhoneNumber;
    private Boolean useEmail;
    private Boolean useGender;
    private Boolean useCaptcha;
    private Boolean useVerifyEmail;
    private Boolean useVerifyPhoneNumber;
    private Boolean useVerifyEmailWhenChanged;
    private Boolean useMagicLink;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<LoginMethod> methods;


    public static TenantRegistrationConfigEntity from(TenantRegistrationConfig registrationConfig) {

        if (registrationConfig == null)
            return null;

        return TenantRegistrationConfigEntity.builder()
                .methods(registrationConfig.getMethods())
                .useMagicLink(registrationConfig.isUseMagicLink())
                .useVerifyEmail(registrationConfig.getVerifyEmail().isRequired())
                .useVerifyEmailWhenChanged(registrationConfig.getVerifyEmailWhenChanged().isRequired())
                .useVerifyPhoneNumber(registrationConfig.getVerifyPhoneNumber().isRequired())
                .useCaptcha(registrationConfig.getCaptcha().isRequired())
                .useGender(registrationConfig.getGender().isRequired())
                .usePhoneNumber(registrationConfig.getPhoneNumber().isRequired())
                .useEmail(registrationConfig.getEmail().isRequired())
                .useLastName(registrationConfig.getLastName().isRequired())
                .useFirstName(registrationConfig.getFirstName().isRequired())
                .useBirthdate(registrationConfig.getBirthdate().isRequired())
                .useUsername(registrationConfig.getUsername().isRequired())
                .build();
    }



}
