package cm.yowyob.auth.app.domain.model.tenant;

import cm.yowyob.auth.app.domain.model.Requirable;
import lombok.*;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantRegistrationConfig {

    @NonNull
    @Builder.Default
    private Requirable username = new Requirable();
    @NonNull
    @Builder.Default
    private Requirable birthdate = new Requirable();
    @NonNull
    @Builder.Default
    private Requirable firstName = new Requirable();
    @NonNull
    @Builder.Default
    private Requirable lastName = new Requirable();
    @NonNull
    @Builder.Default
    private Requirable phoneNumber = new Requirable();
    @NonNull
    @Builder.Default
    private Requirable email = new Requirable(true);
    @NonNull
    @Builder.Default
    private Requirable gender = new Requirable();
    @NonNull
    @Builder.Default
    private Set<LoginMethod> methods = Set.of(LoginMethod.values());

    @NonNull
    @Builder.Default
    private Requirable captcha = new Requirable(false);

    @NonNull
    @Builder.Default
    private Requirable verifyEmail = new Requirable(true);

    @NonNull
    @Builder.Default
    private Requirable verifyPhoneNumber = new Requirable(false);

    @NonNull
    @Builder.Default
    private Requirable verifyEmailWhenChanged = new Requirable(true);

    @Builder.Default
    private boolean useMagicLink = true;



}



