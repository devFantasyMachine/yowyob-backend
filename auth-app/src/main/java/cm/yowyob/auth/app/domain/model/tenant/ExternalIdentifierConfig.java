package cm.yowyob.auth.app.domain.model.tenant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalIdentifierConfig {

    private Integer authorizationGrantIdTimeToLiveInSeconds = 30;
    private Integer changePasswordIdTimeToLiveInSeconds; // change password time to live
    private Integer deviceCodeTimeToLiveInSeconds;
    private Integer emailVerificationIdTimeToLiveInSeconds;
    private Integer externalAuthenticationIdTimeToLiveInSeconds;
    private Integer oneTimePasswordTimeToLiveInSeconds;
    private Integer passwordlessLoginTimeToLiveInSeconds;
    private Integer pendingAccountLinkTimeToLiveInSeconds;
    private Integer registrationVerificationIdTimeToLiveInSeconds = 600;
    private Integer samlv2AuthNRequestIdTimeToLiveInSeconds;
    private Integer setupPasswordIdTimeToLiveInSeconds = 600; // reset or set password id
    private Integer trustTokenTimeToLiveInSeconds;
    private Integer twoFactorIdTimeToLiveInSeconds;
    private Integer twoFactorOneTimeCodeIdTimeToLiveInSeconds;
    private Integer twoFactorTrustIdTimeToLiveInSeconds;
}
