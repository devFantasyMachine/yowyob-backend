package cm.yowyob.auth.app.domain.handlers;


import cm.yowyob.auth.app.domain.api.RegistrationRequest;
import cm.yowyob.auth.app.domain.api.RegistrationResponse;
import cm.yowyob.auth.app.domain.api.ValidateRegistrationRequest;
import cm.yowyob.auth.app.domain.exceptions.*;
import cm.yowyob.auth.app.domain.model.code.DigitCode;
import cm.yowyob.auth.app.domain.model.code.DigitCodeGenerator;
import cm.yowyob.auth.app.domain.model.code.HexCode;
import cm.yowyob.auth.app.domain.model.code.HexCodeGenerator;
import cm.yowyob.auth.app.domain.model.contacts.*;
import cm.yowyob.auth.app.domain.model.message.email.Email;
import cm.yowyob.auth.app.domain.model.message.sms.SMS;
import cm.yowyob.auth.app.domain.model.registration.PasswordHelper;
import cm.yowyob.auth.app.domain.model.registration.Registration;
import cm.yowyob.auth.app.domain.model.registration.RegistrationErrors;
import cm.yowyob.auth.app.domain.model.registration.RegistrationResult;
import cm.yowyob.auth.app.domain.model.tenant.*;
import cm.yowyob.auth.app.domain.model.user.*;
import cm.yowyob.auth.app.domain.port.MessagingService;
import cm.yowyob.auth.app.domain.port.RegistrationService;
import cm.yowyob.auth.app.domain.port.UserService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


@Slf4j
@Builder
@AllArgsConstructor
public class RegistrationManager {

    private final RegistrationService registrationService;
    private final MessagingService messagingService;
    private final UserService userService;
    private final PasswordHelper passwordHelper;
    private final TenantManager tenantManager;


    private final ContactFactory contactFactory = ContactFactory.getInstance();
    private final UserFactory userFactory = new UserFactory();
    private final EmailValidator emailValidator = new EmailValidator();
    private final PhoneValidator phoneValidator = new PhoneValidator();
    private final DigitCodeGenerator digitCodeGenerator = new DigitCodeGenerator();
    private final HexCodeGenerator hexCodeGenerator = new HexCodeGenerator();


    public Set<String> checkRegistrationRequest(@NonNull Tenant tenant,
                                                @NonNull RegistrationRequest request) {

        Set<String> errors = new HashSet<>();

        TenantRegistrationConfig registrationConfig = tenant.getRegistrationConfig();

        Set<LoginMethod> loginMethods = registrationConfig.getMethods();

        if (!loginMethods.contains(request.getMethod())) {
            errors.add(RegistrationErrors.METHOD_NOT_ALLOWED);
            return errors;
        }

        if (request.getMethod().isPasswordMethod()) {

            if (Objects.isNull(request.getPassword())) {
                errors.add(RegistrationErrors.PASSWORD_REQUIRED);
            } else {

                PasswordValidator passwordValidator
                        = new PasswordValidator(tenant.getPasswordConfig());

                if (passwordValidator.isNonValid(request.getPassword())) {
                    errors.add(RegistrationErrors.INVALID_PASSWORD);
                }

            }
        }


        // profile requirements
        if (registrationConfig.getLastName().isRequired() && Objects.isNull(request.getLastName())) {

            errors.add(RegistrationErrors.LASTNAME_REQUIRED);
        }

        if (registrationConfig.getFirstName().isRequired() && Objects.isNull(request.getFirstName())) {

            errors.add(RegistrationErrors.FIRSTNAME_REQUIRED);
        }

        if (registrationConfig.getBirthdate().isRequired() && Objects.isNull(request.getBirthdate())) {

            errors.add(RegistrationErrors.BIRTHDATE_REQUIRED);
        }

        if (registrationConfig.getGender().isRequired() && Objects.isNull(request.getGender())) {

            errors.add(RegistrationErrors.GENDER_REQUIRED);
        }

        // login id requirements
        if (registrationConfig.getPhoneNumber().isRequired() || request.getMethod().isPhoneMethod()) {

            if (Objects.isNull(request.getPhoneNumber())) {
                errors.add(RegistrationErrors.PHONE_NUMBER_REQUIRED);
            } else {

                if (!phoneValidator.isValid(request.getPhoneNumber()))
                    errors.add(RegistrationErrors.INVALID_PHONE_NUMBER);

                Optional<User> optionalUser =
                        userService.getByTenantIdAndPhone(tenant.getId(), request.getPhoneNumber());

                if (optionalUser.isPresent()) {
                    errors.add(RegistrationErrors.PHONE_NUMBER_ALREADY_EXISTS);
                    return errors;
                }
            }
        }

        if (registrationConfig.getEmail().isRequired() || request.getMethod().isEmailMethod()) {

            if (Objects.isNull(request.getEmail())) {
                errors.add(RegistrationErrors.EMAIL_REQUIRED);
            } else {

                if (!emailValidator.isValid(request.getEmail()))
                    errors.add(RegistrationErrors.INVALID_EMAIL);

                Optional<User> optionalUser =
                        userService.getByTenantIdAndEmail(tenant.getId(), request.getEmail());

                if (optionalUser.isPresent()) {
                    errors.add(RegistrationErrors.EMAIL_ALREADY_EXISTS);
                    return errors;
                }
            }
        }

        if (registrationConfig.getUsername().isRequired()) {

            if (Objects.isNull(request.getUsername())) {
                errors.add(RegistrationErrors.USERNAME_REQUIRED);
            } else {

                UsernameValidator usernameValidator =
                        new UsernameValidator(tenant.getUsernameConfig());

                if (usernameValidator.isNonValid(request.getUsername())) {
                    errors.add(RegistrationErrors.INVALID_USERNAME);
                    return errors;
                }

                Optional<User> optionalUser =
                        userService.getByTenantIdAndUsername(tenant.getId(), request.getUsername());

                if (optionalUser.isPresent()) {
                    errors.add(RegistrationErrors.USERNAME_ALREADY_EXISTS);
                    return errors;
                }

            }
        }


        return errors;

    }


    public RegistrationResponse registerTenantUser(@NonNull RegistrationRequest request)
            throws TenantNotFoundException, TenantNotEnabledException {

        Tenant tenant = tenantManager.getTenant(request.getTenantId())
                .orElseThrow(TenantNotFoundException::new);

        if (!tenant.getEnabled())
            throw new TenantNotEnabledException();

        Set<String> errors = checkRegistrationRequest(tenant, request);

        if (!errors.isEmpty())
            return new RegistrationResponse(errors);

        String encodedPassword = request.getMethod().isPasswordMethod() ?
                passwordHelper.encode(request.getPassword()) : passwordHelper.randomPassword();

        TenantRegistrationConfig registrationConfig = tenant.getRegistrationConfig();
        Set<RequireAction> requireActions = getRequiredActions(request, registrationConfig);

        if (requireActions.isEmpty()) {

            User activeUser = createActiveUser(request, encodedPassword);
            userService.save(activeUser);

            return new RegistrationResponse(true, null, null);
        }

        ExternalIdentifierConfig externalIdentifierConfig = tenant.getExternalIdentifierConfig();

        DigitCode digitCode = digitCodeGenerator.generate();
        HexCode hexCode = hexCodeGenerator.generate(32);

        Duration duration = Duration.ofSeconds(externalIdentifierConfig.getRegistrationVerificationIdTimeToLiveInSeconds());

        Registration registration = new Registration(
                hexCode.getValue(),
                request.getTenantId(),
                Instant.now().plus(duration),
                request.getUsername(),
                encodedPassword,
                request.getEmail(),
                request.getPhoneNumber(),
                request.getFirstName(),
                request.getLastName(),
                request.getGender(),
                request.getBirthdate(),
                request.getTimezone(),
                request.getDetails().getUserAgent(),
                request.getDetails().getUserIp(),
                passwordHelper.encode(digitCode.getValue())
        );

        registration.setLoginMethod(request.getMethod());
        registration.setRequireActions(requireActions);

        Registration savedRegistration = registrationService.save(registration);

        if (requireActions.contains(RequireAction.EMAIL_VERIFICATION)) {

            EmailConfiguration emailConfiguration = tenant.getEmailConfiguration();

            if (registrationConfig.isUseMagicLink()) {

                sendMagicLinkByEmail(request.getEmail(),
                        emailConfiguration,
                        tenant.getIssuer(),
                        registration.getId(),
                        digitCode);

            } else {

                sendDigitCodeByEmail(request.getEmail(),
                        emailConfiguration,
                        digitCode);
            }

        } else if (requireActions.contains(RequireAction.PHONE_NUMBER_VERIFICATION)) {

            sendDigitCodeBySMS(request.getPhoneNumber(), digitCode);
        }


        return RegistrationResponse.builder()
                .isSuccess(true)
                .verificationId(savedRegistration.getId())
                .build();

    }

    private User createActiveUser(RegistrationRequest request, String encodedPassword) {

        User user = userFactory.createUser(
                request.getTenantId(),
                request.getUsername(),
                encodedPassword,
                request.getEmail(),
                request.getPhoneNumber(),
                null
        );

        Profile profile = Profile.builder()
                .version(0)
                .birthdate(request.getBirthdate())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .timezone(request.getTimezone())
                .gender(request.getGender())
                .build();

        user.withProfile(profile);
        UserState userState = user.activate(UserState.StateReason.ACCOUNT_VERIFICATION, null);

        userService.save(userState);
        return user;
    }



    private static Set<RequireAction> getRequiredActions(RegistrationRequest request,
                                                         TenantRegistrationConfig registrationConfig) {

        if (request.getMethod().nonRequireVerification())
            return Set.of();

        Set<RequireAction> requireActions = new HashSet<>();

        if (request.getMethod().isEmailMethod()
                && registrationConfig.getVerifyEmail().isRequired())
            requireActions.add(RequireAction.EMAIL_VERIFICATION);

        if (request.getMethod().isPhoneMethod()
                && registrationConfig.getVerifyPhoneNumber().isRequired())
            requireActions.add(RequireAction.PHONE_NUMBER_VERIFICATION);

        return Collections.unmodifiableSet(requireActions);
    }





    private void sendDigitCodeBySMS(String phoneNumber, @NonNull DigitCode digitCode) {

        String txt = digitCode.getValue() + "is your verification code";

        SMS sms = SMS.builder()
                .content(txt)
                .to((PhoneNumber) contactFactory.createPhoneNumber(phoneNumber))
                .build();

        messagingService.sendSMS(sms);
    }


    private void sendDigitCodeByEmail(String userEmail,
                                      @NonNull EmailConfiguration emailConfiguration,
                                      @NonNull DigitCode digitCode) {

        String txt = "To complete your registration verification" +
                " enter this code into the registration " +
                "verification from " + digitCode.getValue();


        EmailAddress to = (EmailAddress) contactFactory.createEmailAddress(userEmail);
        EmailAddress from = (EmailAddress) contactFactory.createEmailAddress(emailConfiguration.defaultFromEmail);

        Email email = Email.builder()
                .to(List.of(to))
                .from(from)
                .subject("Account Verification")
                .text(txt)
                .build();

        messagingService.sendEmail(email, emailConfiguration);
    }

    private void sendMagicLinkByEmail(String userEmail,
                                      @NonNull EmailConfiguration emailConfiguration,
                                      String issuer,
                                      String verificationId,
                                      @NonNull DigitCode digitCode) {


        String token = verificationId + ":" + digitCode.getValue();
        token = Base64.getUrlEncoder().encodeToString(token.getBytes());

        String txt = "Verify your account with this link: http://" + issuer + "/email-verification?token=" + token;

        EmailAddress to = (EmailAddress) contactFactory.createEmailAddress(userEmail);
        EmailAddress from = (EmailAddress) contactFactory.createEmailAddress(emailConfiguration.defaultFromEmail);

        Email email = Email.builder()
                .to(List.of(to))
                .from(from)
                .subject("Account Verification")
                .text(txt)
                .build();

        messagingService.sendEmail(email, emailConfiguration);
    }




    public Registration getRegistration(String id) throws RegistrationNotFoundException {

        return registrationService.getById(id)
                .filter(Registration::isNonUsed)
                .orElseThrow(RegistrationNotFoundException::new);
    }


    private Registration getValidRegistration(String id, String challenge)
            throws RegistrationNotFoundException,
            RegistrationExpiredException, InvalidChallengeException {

        log.info("validate user account with registration {}", id);

        Registration registration = getRegistration(id);
        log.info("retrieve registration, {} successfully", registration);

        if (registration.isExpired()) {
            registration.reject();
            registrationService.save(registration);
            log.error("registration has expired");
            throw new RegistrationExpiredException();
        }

        log.info("attempt to match challenge with code {}", challenge);

        if (!passwordHelper.matches(challenge, registration.getRegistrationCode()))
            throw new InvalidChallengeException();

        return registration;

    }


    public RegistrationResult validateRegistration(ValidateRegistrationRequest request)
            throws RegistrationNotFoundException,
            RegistrationExpiredException,
            InvalidChallengeException {

        Registration validRegistration =
                getValidRegistration(request.getVerificationId(), request.getVerificationCode());

        User user = userFactory.createUser(
                validRegistration.getTenantId(),
                validRegistration.getUsername(),
                validRegistration.getEncodedPassword(),
                validRegistration.getEmail(),
                validRegistration.getPhone(),
                null
        );

        Profile profile = Profile.builder()
                .version(0)
                .birthdate(validRegistration.getBirthdate())
                .firstName(validRegistration.getFirstName())
                .lastName(validRegistration.getLastName())
                .timezone(validRegistration.getTimezone())
                .gender(validRegistration.getGender())
                .build();

        user.withProfile(profile);

        if (validRegistration.getLoginMethod().isEmailMethod()) {
            user.setEmailVerified(true);
            validRegistration.removeAction(RequireAction.EMAIL_VERIFICATION);
        }

        if (validRegistration.getLoginMethod().isPhoneMethod()) {
            user.setPhoneVerified(true);
            validRegistration.removeAction(RequireAction.PHONE_NUMBER_VERIFICATION);
        }

        user.addRequireAction(validRegistration.getRequireActions());

        userService.save(user.activate(UserState.StateReason.ACCOUNT_VERIFICATION, null));
        User savedUser = userService.save(user);

        validRegistration.accept();
        registrationService.save(validRegistration);

        return RegistrationResult.builder()
                .user(savedUser)
                .redirectUri(validRegistration.getRedirectUri())
                .build();
    }





}
