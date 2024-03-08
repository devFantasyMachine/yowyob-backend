package cm.yowyob.auth.app.domain.auth.login;

import cm.yowyob.auth.app.domain.auth.AuthErrors;
import cm.yowyob.auth.app.domain.auth.SessionTokenGenerator;
import cm.yowyob.auth.app.domain.exceptions.AuthenticationException;
import cm.yowyob.auth.app.domain.handlers.DeviceManager;
import cm.yowyob.auth.app.domain.handlers.TenantManager;
import cm.yowyob.auth.app.domain.auth.AuthenticationResult;
import cm.yowyob.auth.app.domain.auth.Authenticator;
import cm.yowyob.auth.app.domain.handlers.UserManager;
import cm.yowyob.auth.app.domain.model.registration.PasswordHelper;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.user.User;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class LoginAuthenticator extends Authenticator<LoginCredentials> {

    private static final Logger log = LoggerFactory.getLogger(LoginAuthenticator.class);

    private final PasswordHelper passwordHelper;


    public LoginAuthenticator(DeviceManager deviceManager,
                              PasswordHelper passwordHelper,
                              SessionTokenGenerator sessionTokenGenerator,
                              TenantManager tenantManager,
                              UserManager userManager) {
        super(deviceManager, tenantManager, sessionTokenGenerator, userManager);
        this.passwordHelper = passwordHelper;
    }


    @Override
    public AuthenticationResult attemptAuthenticate(Tenant tenant, LoginCredentials request)
            throws AuthenticationException {

        log.info("attempt to attemptAuthenticate user with {}", request);

        User user = userManager.getByTenantIdAndEmail(request.getTenantId(), request.getUsername())
                .or(() -> userManager.getByTenantIdAndPhone(request.getTenantId(), request.getUsername()))
                .or(() -> userManager.getByTenantIdAndUsername(request.getTenantId(), request.getUsername()))
                .orElseThrow(() -> new AuthenticationException(AuthErrors.USER_NOT_FOUND));

        log.info("user found {}", user);

        if (!passwordHelper.matches((String) request.getChallenge(), user.getPassword())) {

            log.error("invalid password");
            throw new AuthenticationException(AuthErrors.CHALLENGE_NOT_VALID);
        }

        log.info("password match successfully !");

        return AuthenticationResult.builder()
                .user(user)
                .build();

    }

}
