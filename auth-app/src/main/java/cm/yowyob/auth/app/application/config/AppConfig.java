package cm.yowyob.auth.app.application.config;

import cm.yowyob.auth.app.domain.auth.SessionTokenGenerator;
import cm.yowyob.auth.app.domain.auth.invitation.InvitationAuthenticator;
import cm.yowyob.auth.app.domain.auth.login.LoginAuthenticator;
import cm.yowyob.auth.app.domain.auth.registration.RegistrationAuthenticator;
import cm.yowyob.auth.app.domain.auth.resetpassword.ResetPasswordAuthenticator;
import cm.yowyob.auth.app.domain.handlers.*;
import cm.yowyob.auth.app.domain.model.device.UserAgentParser;
import cm.yowyob.auth.app.domain.model.notification.Notification;
import cm.yowyob.auth.app.domain.model.registration.PasswordHelper;
import cm.yowyob.auth.app.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;


@Configuration
public class AppConfig {


    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of("Africa/Douala"));
    }


    @Bean
    public AdminHandler adminHandler(UserService userService,
                                     InvitationManager invitationManager,
                                     TenantManager tenantManager,
                                     PasswordHelper passwordHelper) {

        return new AdminHandler(
                passwordHelper,
                invitationManager,
                userService,
                tenantManager);

    }


    @Bean
    public TenantManager tenantManager(UserService userService,
                                       TenantService tenantService) {

        return new TenantManager(
                tenantService,
                userService
        );

    }


    @Bean
    public UserManager userManager(UserService userService,
                                   PasswordHelper passwordHelper) {

        return new UserManager(
                userService,
                passwordHelper
        );

    }

    @Bean
    public InvitationManager invitationHandler(UserService userService,
                                               PasswordHelper passwordHelper,
                                               MessagingService messagingService,
                                               InvitationService invitationService) {

        return InvitationManager.builder()
                .invitationService(invitationService)
                .userService(userService)
                .passwordHelper(passwordHelper)
                .messagingService(messagingService)
                .build();
    }


    @Bean
    public APIKeyManager apiKeyManager(UserService userService, APIKeyService apiKeyService) {

        return new APIKeyManager(userService, apiKeyService);
    }

    @Bean
    public DeviceManager deviceManager(DeviceService deviceService,
                                       GeoIPHelper geoIPHelper,
                                       UserAgentParser userAgentParser) {

        return new DeviceManager(deviceService,
                geoIPHelper,
                userAgentParser,
                new NotificationProducer() {
                    @Override
                    public void produce(Notification notification) {
                        NotificationProducer.super.produce(notification);
                    }
                });
    }


    @Bean
    public RegistrationManager registrationManager(
            RegistrationService registrationService,
            MessagingService messagingService,
            UserService userService,
            PasswordHelper passwordHelper,
            TenantManager tenantManager) {

        return new RegistrationManager(registrationService,
                messagingService,
                userService,
                passwordHelper,
                tenantManager);
    }

    @Bean
    public ApplicationManager applicationManager(
            GroupService groupService,
            UserService userService,
            TenantManager tenantManager,
            ApplicationService applicationService
    ) {

        return new ApplicationManager(groupService, userService, tenantManager, applicationService);
    }

    @Bean
    public InvitationAuthenticator invitationAuthenticator(
            InvitationManager invitationManager,
            DeviceManager deviceManager,
            SessionTokenGenerator sessionTokenGenerator,
            TenantManager tenantManager,
            UserManager userManager
    ) {

        return new InvitationAuthenticator(invitationManager,
                deviceManager,
                sessionTokenGenerator,
                tenantManager,
                userManager);
    }

    @Bean
    public ResetPasswordAuthenticator resetPasswordAuthenticator(
            DeviceManager deviceManager,
            SessionTokenGenerator sessionTokenGenerator,
            TenantManager tenantManager,
            UserManager userManager
    ){

        return new ResetPasswordAuthenticator(deviceManager, sessionTokenGenerator, tenantManager, userManager);
    }


    @Bean
    public LoginAuthenticator loginAuthenticator(
            UserManager userManager,
            PasswordHelper passwordHelper,
            DeviceManager deviceManager,
            SessionTokenGenerator sessionTokenGenerator,
            TenantManager tenantManager) {

        return new LoginAuthenticator(deviceManager,
                passwordHelper,
                sessionTokenGenerator,
                tenantManager,
                userManager);
    }


    @Bean
    public RegistrationAuthenticator registrationAuthenticator(
            RegistrationManager registrationManager,
            DeviceManager deviceManager,
            SessionTokenGenerator sessionTokenGenerator,
            UserManager userManager,
            TenantManager tenantManager) {

        return new RegistrationAuthenticator(deviceManager,
                registrationManager,
                sessionTokenGenerator,
                tenantManager,
                userManager);
    }


    @Bean
    public UserApplicationRegistrationManager userApplicationRegistrationManager(
            ApplicationManager applicationManager,
            UserManager userManager,
            UserRegistrationService userRegistrationService
    ) {

        return new UserApplicationRegistrationManager(applicationManager,
                userManager,
                userRegistrationService);
    }


    @Bean
    public OAuthClientManager oAuthClientManager(
            OAuthClientService oAuthClientService,
            TenantManager tenantManager,
            ApplicationManager appManager,
            GroupService groupService) {

        return new OAuthClientManager(oAuthClientService, tenantManager, appManager, groupService);
    }


}
