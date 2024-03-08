package cm.yowyob.auth.app.domain.model.tenant;


import cm.yowyob.auth.app.domain.model.RateLimitedRequestConfiguration;
import lombok.Data;


@Data
public class TenantRateLimitConfig {

    public RateLimitedRequestConfiguration failedLogin = new RateLimitedRequestConfiguration(5, 60);
    public RateLimitedRequestConfiguration forgotPassword = new RateLimitedRequestConfiguration(5, 60);
    public RateLimitedRequestConfiguration sendEmailVerification = new RateLimitedRequestConfiguration(5, 60);
    public RateLimitedRequestConfiguration sendPasswordless = new RateLimitedRequestConfiguration(5, 60);
    public RateLimitedRequestConfiguration sendRegistrationVerification = new RateLimitedRequestConfiguration(5, 60);
    public RateLimitedRequestConfiguration sendTwoFactor = new RateLimitedRequestConfiguration(5, 60);



    public RateLimitedRequestConfiguration getConfiguration(RateLimitedRequestType var1) {
        return switch (var1) {
            case FailedLogin -> this.failedLogin;
            case ForgotPassword -> this.forgotPassword;
            case SendEmailVerification -> this.sendEmailVerification;
            case SendPasswordless -> this.sendPasswordless;
            case SendRegistrationVerification -> this.sendRegistrationVerification;
            case SendTwoFactor -> this.sendTwoFactor;
            default -> throw new IllegalArgumentException("Unexpected request type [" + var1 + "].");
        };
    }



    public enum RateLimitedRequestType {
        FailedLogin,
        ForgotPassword,
        SendEmailVerification,
        SendPasswordless,
        SendRegistrationVerification,
        SendTwoFactor;

        RateLimitedRequestType() {
        }
    }

}
