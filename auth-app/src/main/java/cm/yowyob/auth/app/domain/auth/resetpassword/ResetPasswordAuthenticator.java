package cm.yowyob.auth.app.domain.auth.resetpassword;


import cm.yowyob.auth.app.domain.api.ValidateResetPasswordRequest;
import cm.yowyob.auth.app.domain.auth.AuthenticationResult;
import cm.yowyob.auth.app.domain.auth.Authenticator;
import cm.yowyob.auth.app.domain.auth.SessionTokenGenerator;
import cm.yowyob.auth.app.domain.exceptions.AuthenticationException;
import cm.yowyob.auth.app.domain.handlers.DeviceManager;
import cm.yowyob.auth.app.domain.handlers.TenantManager;
import cm.yowyob.auth.app.domain.handlers.UserManager;
import cm.yowyob.auth.app.domain.model.ResetPasswordChallenge;
import cm.yowyob.auth.app.domain.model.tenant.PasswordValidator;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.user.RequireAction;
import cm.yowyob.auth.app.domain.model.user.User;
import lombok.extern.slf4j.Slf4j;




@Slf4j
public class ResetPasswordAuthenticator extends Authenticator<ValidateResetPasswordRequest> {


    public ResetPasswordAuthenticator(DeviceManager deviceManager,
                                      SessionTokenGenerator sessionTokenGenerator,
                                      TenantManager tenantManager,
                                      UserManager userManager) {
        super(deviceManager, tenantManager, sessionTokenGenerator, userManager);
    }


    @Override
    protected AuthenticationResult attemptAuthenticate(Tenant tenant, ValidateResetPasswordRequest challenge) throws AuthenticationException {


        log.info("validate user account with reset password id  {}", challenge.getId());

        ResetPasswordChallenge resetPasswordChallenge
                = userManager.getResetPasswordById(challenge.getVerificationId())
                .orElseThrow(() -> new AuthenticationException("NotFound"));

        log.info("retrieve resetPasswordChallenge, {} successfully", resetPasswordChallenge);

        if (resetPasswordChallenge.isExpired()) {
            resetPasswordChallenge.reject();
            userManager.save(resetPasswordChallenge);
            log.error("resetPasswordChallengeId has expired");
            throw new AuthenticationException("reset password Expired");
        }

        PasswordValidator passwordValidator = new PasswordValidator(tenant.getPasswordConfig());

        if (passwordValidator.isNonValid(challenge.getPassword())) {
            throw new AuthenticationException("INVALID_PASSWORD");
        }

        User user = resetPasswordChallenge.getTarget();
        user.removeAction(RequireAction.RESET_PASSWORD);

        User savedUser = userManager.resetPassword(user, challenge.getPassword());

        log.info("reset user password successfully");

        resetPasswordChallenge.accept();
        userManager.save(resetPasswordChallenge);

        return AuthenticationResult.builder()
                .user(savedUser)
                .build();
    }




}
