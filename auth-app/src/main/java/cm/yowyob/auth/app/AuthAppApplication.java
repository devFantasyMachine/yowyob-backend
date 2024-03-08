package cm.yowyob.auth.app;

import cm.yowyob.auth.app.domain.exceptions.TenantAlreadyExistsException;
import cm.yowyob.auth.app.domain.exceptions.UserAlreadyExistException;
import cm.yowyob.auth.app.domain.handlers.APIKeyManager;
import cm.yowyob.auth.app.domain.handlers.AdminHandler;
import cm.yowyob.auth.app.domain.handlers.TenantManager;
import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.contacts.ContactFactory;
import cm.yowyob.auth.app.domain.model.contacts.EmailAddress;
import cm.yowyob.auth.app.domain.model.oauth.client.ClientSettings;
import cm.yowyob.auth.app.domain.model.oauth.client.OAuthClient;
import cm.yowyob.auth.app.domain.model.oauth.client.TokenSettings;
import cm.yowyob.auth.app.domain.model.oauth.core.AuthorizationGrantType;
import cm.yowyob.auth.app.domain.model.oauth.core.ClientAuthenticationMethod;
import cm.yowyob.auth.app.domain.model.tenant.Organisation;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.token.APIKey;
import cm.yowyob.auth.app.domain.model.token.Permission;
import cm.yowyob.auth.app.domain.model.user.Address;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.port.OAuthClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.security.oauth2.core.oidc.OidcScopes;

import java.util.Set;
import java.util.UUID;


@Slf4j
@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication()
public class AuthAppApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(AuthAppApplication.class, args);
    }


    private final ContactFactory contactFactory = ContactFactory.getInstance();


    @Autowired
    AdminHandler adminHandler;

    @Autowired
    TenantManager tenantManager;

    @Autowired
    APIKeyManager apiKeyManager;


    @Autowired
    OAuthClientService oAuthClientService;



    @Override
    public void run(String... args) {

        Tenant defaultTenant;

        try {

            Organisation organisation = Organisation.builder()
                    .shortName("Yowyob")
                    .name("Your Way Your Business")
                    .description("")
                    .support(contactFactory.createEmailAddress("cteuboutonzong@gmail.com"))
                    .location(Address.builder()
                            .timezone("Africa/Douala")
                            .country("Cameroon")
                            .region("Center")
                            .city("Yaounde")
                            .build()
                    )
                    .build();

            defaultTenant = tenantManager.createDefaultTenant(organisation,"localhost:3000");
            Tenant activedDefaultTenant = tenantManager.save(defaultTenant.activate());

            log.info("create default tenant successfully");

            defaultTenant = activedDefaultTenant;

        } catch (TenantAlreadyExistsException e) {

            log.warn("Default tenant already exists", e);
            defaultTenant = tenantManager.getDefault();
        }

        if (defaultTenant == null) {
            throw new RuntimeException("Default tenant not found");
        }

        Contact contact = contactFactory.createEmailAddress("devfantasymachine@gmail.com");

        try {

            log.info("attempt to create super admin");
            User superAdmin = adminHandler.createSuperAdmin(defaultTenant.getId(), (EmailAddress) contact);

            log.info("create super admin successfully");
            log.warn("super admin " + superAdmin.getUsername() + " must reset their password");

            APIKey apiKey = apiKeyManager.createAPIKey(
                    superAdmin.getUserId(),
                    defaultTenant.getId(),
                    Permission.ALL,
                    Set.of("127.0.0.1")
            );

            System.out.println("system key is " +  apiKey);

        } catch (UserAlreadyExistException e) {
            log.warn("super admin already exists");
        } catch (Exception e) {
            log.warn("error on create super admin process", e);
            throw new RuntimeException();
        }


        OAuthClient oAuthClient = OAuthClient.withId("3c34c114-d977-3cda-a4ee-46c50af1f567")
                .clientId("business-client")
                .clientSecret("{noop}secret")
                .tenantId(Tenant.DEFAULT_TENANT_ID)
                .applicationId(UUID.fromString("b146021c-0842-444d-b45e-89af16f022d8"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")
                .redirectUri("http://192.168.43.161:3000/api/auth/callback/yowyob")
                .redirectUri("https://oidcdebugger.com/debug")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .tokenSettings(TokenSettings.builder()
                        .build()
                )
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build()
                )
                .build();

        //oAuthClientService.save(oAuthClient);

    }


}


