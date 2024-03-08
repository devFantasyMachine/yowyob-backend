package cm.yowyob.auth.app.application.controllers;


import cm.yowyob.auth.app.application.dto.ApplicationDTO;
import cm.yowyob.auth.app.application.dto.GeneralAppInfoDTO;
import cm.yowyob.auth.app.application.utils.HttpHelper;
import cm.yowyob.auth.app.domain.api.CreateOAuthClientRequest;
import cm.yowyob.auth.app.domain.exceptions.*;
import cm.yowyob.auth.app.domain.handlers.ApplicationManager;
import cm.yowyob.auth.app.domain.handlers.OAuthClientManager;
import cm.yowyob.auth.app.domain.handlers.UserApplicationRegistrationManager;
import cm.yowyob.auth.app.domain.model.application.Application;
import cm.yowyob.auth.app.domain.model.application.GeneralAppInfo;
import cm.yowyob.auth.app.domain.model.contacts.ContactFactory;
import cm.yowyob.auth.app.domain.model.oauth.client.OAuthClient;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.model.user.UserRegistration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/apps")
public class ApplicationController {


    @Autowired
    ApplicationManager applicationManager;

    @Autowired
    UserApplicationRegistrationManager userApplicationRegistrationManager;

    @Autowired
    OAuthClientManager oAuthClientManager;

    private final ContactFactory contactFactory = ContactFactory.getInstance();




    @PostMapping("")
    public ApplicationDTO createApp(
            @RequestBody @NotNull GeneralAppInfoDTO infoDTO,
            Authentication authentication,
            HttpServletRequest httpServletRequest)
            throws UserNotFoundException, TenantNotFoundException {

        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);
        UserId userId = UserId.of(authentication.getName());

        Application app = applicationManager.createApp(
                tenantId,
                userId,
                GeneralAppInfo.builder()
                        .icon(infoDTO.getIcon())
                        .description(infoDTO.getDescription())
                        .homePage(infoDTO.getHomePage())
                        .shortName(infoDTO.getShortName())
                        .name(infoDTO.getName())
                        .privacyPolicyLink(infoDTO.getPrivacyPolicyLink())
                        .termOfUseLink(infoDTO.getTermOfUseLink())
                        .support(infoDTO.getSupport() == null ? null :
                                contactFactory.createEmailAddress(infoDTO.getSupport())
                        )
                        .build());

        return ApplicationDTO.from(app);
    }


    @GetMapping("/{appId}")
    public ApplicationDTO getApplication(@PathVariable @NotNull UUID appId)
            throws ApplicationNotFoundException {

        return ApplicationDTO.from(applicationManager.getById(appId));

    }

    @GetMapping("/developer")
    public List<ApplicationDTO> getDeveloperApplication(Authentication authentication)
            throws ApplicationNotFoundException {

        UserId userId = UserId.of(authentication.getName());
        return applicationManager.getDeveloperApplication(userId)
                .stream()
                .map(ApplicationDTO::from)
                .collect(Collectors.toList());

    }


    @PutMapping("/{appId}")
    public ApplicationDTO updateApplication(Authentication authentication,
                                            HttpServletRequest httpServletRequest,
                                            @PathVariable @NotNull UUID appId,
                                            @RequestBody @NotNull @NonNull GeneralAppInfoDTO info)
            throws ApplicationNotFoundException, UserActionNotAllowedException {

        UserId userId = UserId.of(authentication.getName());

        Application app = applicationManager.updateGeneralInfo(
                userId,
                appId,
                GeneralAppInfo.builder()
                        .icon(info.getIcon())
                        .description(info.getDescription())
                        .homePage(info.getHomePage())
                        .shortName(info.getShortName())
                        .name(info.getName())
                        .privacyPolicyLink(info.getPrivacyPolicyLink())
                        .termOfUseLink(info.getTermOfUseLink())
                        .build()
        );

        return ApplicationDTO.from(app);

    }


    @PostMapping("/{appId}/registrations")
    public UserRegistration registerToApp(Authentication authentication,
                                          HttpServletRequest httpServletRequest,
                                          @PathVariable @NotNull UUID appId)
            throws UserNotFoundException, UserBirthDayNullException,
            ApplicationNotFoundException, UserParentEmailRequiredException,
            UserRegistrationNotAvailableForChildException {

        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);
        UserId userId = UserId.of(authentication.getName());

        return userApplicationRegistrationManager.registerToApp(appId, tenantId, userId);

    }


    @GetMapping("/{appId}/registrations")
    public List<UserRegistration> retrieves(Authentication authentication,
                                            HttpServletRequest httpServletRequest,
                                            @PathVariable @NotNull UUID appId) {

        return userApplicationRegistrationManager.retrieves(appId);

    }


    @PostMapping("/{appId}/oauth/client")
    public OAuthClient createOAuthClient(Authentication authentication,
                                         HttpServletRequest httpServletRequest,
                                         @PathVariable @NotNull UUID appId,
                                         @RequestBody CreateOAuthClientRequest clientRequest)
            throws ApplicationNotFoundException, UserActionNotAllowedException, TenantNotFoundException {

        UUID tenantId = HttpHelper.getTenantId(httpServletRequest);
        UserId userId = UserId.of(authentication.getName());

        return oAuthClientManager.addClientToApp(tenantId, appId, userId, clientRequest);

    }









}
