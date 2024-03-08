package cm.yowyob.auth.app.domain.handlers;


import cm.yowyob.auth.app.domain.exceptions.*;
import cm.yowyob.auth.app.domain.model.application.Application;
import cm.yowyob.auth.app.domain.model.application.ApplicationRole;
import cm.yowyob.auth.app.domain.model.tenant.FamilyConfig;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.model.user.UserRegistration;
import cm.yowyob.auth.app.domain.port.UserRegistrationService;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;



@AllArgsConstructor
public class UserApplicationRegistrationManager {


    private ApplicationManager applicationManager;
    private UserManager userManager;
    private UserRegistrationService userRegistrationService;




    public List<UserRegistration> retrieves(UUID appId){

        return userRegistrationService.getAllByAppId(appId);
    }


    public UserRegistration registerToApp(UUID appId, UUID tenantId, UserId userId)
            throws UserNotFoundException, ApplicationNotFoundException,
            UserBirthDayNullException, UserRegistrationNotAvailableForChildException,
            UserParentEmailRequiredException {

        Optional<UserRegistration> optionalUserRegistration = getUserRegistration(appId, userId);

        if (optionalUserRegistration.isPresent())
            return optionalUserRegistration.get();

        User user = userManager.getByTenantIdAndUserId(tenantId, userId)
                .orElseThrow(UserNotFoundException::new);

        Application application = applicationManager.getById(appId);
        FamilyConfig familyConfig = application.getFamilyConfig();

        if (familyConfig.getEnabled() && isChild(user, familyConfig)) {

            if (familyConfig.getAllowChildRegistrations()) {

                if (!user.hasParentEmail())
                    throw new UserParentEmailRequiredException();

                // TODO: 02/02/2024 send email to parent for consent




            }

            throw new UserRegistrationNotAvailableForChildException();

        } else {

            Set<String> defaultRoles = application.getRoles()
                    .stream()
                    .filter(ApplicationRole::getIsDefault)
                    .map(ApplicationRole::getName)
                    .collect(Collectors.toSet());

            UserRegistration userRegistration = UserRegistration.builder()
                    .id(UUID.randomUUID())
                    .applicationId(appId)
                    .insertInstant(ZonedDateTime.now())
                    .user(user)
                    .roles(defaultRoles)
                    .build();

            UserRegistration savedRegistration = userRegistrationService.save(userRegistration);

            user.addRoles(new ArrayList<>(defaultRoles));
            userManager.save(user);

            return savedRegistration;

        }

    }



    public Optional<UserRegistration> getUserRegistration(UUID appId, UserId userId) {
        // find old registration
        return userRegistrationService.getByApplicationIdAndUserId(appId, userId);
    }



    private boolean isChild(User user, FamilyConfig familyConfig) throws UserBirthDayNullException {

        LocalDate birthdate = user.getProfile().getBirthdate();

        if (birthdate == null)
            throw new UserBirthDayNullException();

        return LocalDate.now()
                .minusYears(familyConfig.getMaximumChildAge())
                .isAfter(birthdate);
    }



    private boolean isOwner(User user, FamilyConfig familyConfig) throws UserBirthDayNullException {

        LocalDate birthdate = user.getProfile().getBirthdate();

        if (birthdate == null)
            throw new UserBirthDayNullException();

        return LocalDate.now()
                .minusYears(familyConfig.getMinimumOwnerAge())
                .isAfter(birthdate);
    }



}
