package cm.yowyob.auth.app.domain.auth.registration;

import cm.yowyob.auth.app.domain.api.ValidateRegistrationRequest;
import cm.yowyob.auth.app.domain.auth.AuthErrors;
import cm.yowyob.auth.app.domain.auth.SessionTokenGenerator;
import cm.yowyob.auth.app.domain.exceptions.*;
import cm.yowyob.auth.app.domain.handlers.DeviceManager;
import cm.yowyob.auth.app.domain.handlers.RegistrationManager;
import cm.yowyob.auth.app.domain.handlers.TenantManager;
import cm.yowyob.auth.app.domain.auth.AuthenticationResult;
import cm.yowyob.auth.app.domain.auth.Authenticator;
import cm.yowyob.auth.app.domain.handlers.UserManager;
import cm.yowyob.auth.app.domain.model.registration.RegistrationResult;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;

import java.util.Base64;


public class RegistrationAuthenticator extends Authenticator<ValidateRegistrationRequest> {

    private final RegistrationManager registrationManager;

    public RegistrationAuthenticator(DeviceManager deviceManager,
                                     RegistrationManager registrationManager,
                                     SessionTokenGenerator sessionTokenGenerator,
                                     TenantManager tenantManager,
                                     UserManager userManager) {
        super(deviceManager, tenantManager, sessionTokenGenerator, userManager);
        this.registrationManager = registrationManager;
    }



    @Override
    public AuthenticationResult attemptAuthenticate(Tenant tenant, ValidateRegistrationRequest request)
            throws AuthenticationException {

        try {

            String verificationId = request.getVerificationId();
            String digitCode = request.getVerificationCode();

            if (digitCode == null){

                String token = request.getVerificationId();
                token = new String(Base64.getUrlDecoder().decode(token.getBytes()));

                String[] credentials = token.split(":");

                if (credentials.length < 2)
                    throw new AuthenticationException("invalid");

                verificationId = credentials[0];
                digitCode = credentials[1];

            }

            ValidateRegistrationRequest validateRegistrationRequest
                    = new ValidateRegistrationRequest(request.getDetails(), verificationId, digitCode);

            RegistrationResult result = registrationManager.validateRegistration(validateRegistrationRequest);

            return AuthenticationResult.builder()
                    .user(result.getUser())
                    .redirectUri(result.getRedirectUri())
                    .build();

        } catch (RegistrationNotFoundException e) {
            throw new AuthenticationException(AuthErrors.REGISTRATION_NOT_FOUND);
        } catch (RegistrationExpiredException e) {
            throw new AuthenticationException("expired");
        } catch (InvalidChallengeException e) {
            throw new AuthenticationException("invalid");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new AuthenticationException(AuthErrors.FATAL_ERROR);
        }

    }



}
