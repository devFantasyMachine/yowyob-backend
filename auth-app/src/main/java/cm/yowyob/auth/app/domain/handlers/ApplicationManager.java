package cm.yowyob.auth.app.domain.handlers;


import cm.yowyob.auth.app.domain.exceptions.ApplicationNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.TenantNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.UserActionNotAllowedException;
import cm.yowyob.auth.app.domain.exceptions.UserNotFoundException;
import cm.yowyob.auth.app.domain.model.TimeBasedDeletePolicy;
import cm.yowyob.auth.app.domain.model.application.Application;
import cm.yowyob.auth.app.domain.model.application.ApplicationRole;
import cm.yowyob.auth.app.domain.model.application.GeneralAppInfo;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.user.Group;
import cm.yowyob.auth.app.domain.model.user.GroupMember;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.port.ApplicationService;
import cm.yowyob.auth.app.domain.port.GroupService;
import cm.yowyob.auth.app.domain.port.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Builder
@AllArgsConstructor
public class ApplicationManager {


    private GroupService groupService;
    private UserService userService;
    private TenantManager tenantManager;
    private ApplicationService applicationService;




    public Application getById(UUID appId) throws ApplicationNotFoundException {

        return applicationService.getById(appId)
                .orElseThrow(ApplicationNotFoundException::new);
    }



    public Application createApp(UUID tenantId,
                                 UserId creator,
                                 GeneralAppInfo appInfo)
            throws UserNotFoundException, TenantNotFoundException {

        User developer = userService.getDeveloper(tenantId, creator);

        Tenant tenant = tenantManager.getTenant(tenantId).orElseThrow(TenantNotFoundException::new);

        Application newApp = Application.builder()
                .id(UUID.randomUUID())
                .tenantId(tenant.getId())
                .creator(developer)
                .verifyRegistration(false)
                .active(tenant.getConfigured())
                .info(appInfo)
                .build();

        Application savedApp = applicationService.save(newApp);

        ApplicationRole userRole = ApplicationRole.createUserRole(newApp);
        ApplicationRole adminRole = ApplicationRole.createAdminRole(newApp);
        ApplicationRole superAdminRole = ApplicationRole.createSuperAdminRole(newApp);
        ApplicationRole developerRole = ApplicationRole.createDeveloperRole(newApp);

        applicationService.saveAll(Set.of(userRole,adminRole, superAdminRole, developerRole));

        newApp.setRoles(List.of(userRole, adminRole, superAdminRole, developerRole));

        Group developerGroup = Group.builder()
                .groupId(UUID.randomUUID())
                .appId(savedApp.getId())
                .tenantId(tenantId)
                .creator(creator)
                .name("Developer's Group")
                .desc("developer group")
                .roles(List.of(developerRole))
                .groupType(Group.GroupType.STAFF)
                .build();

        Group adminGroup = Group.builder()
                .groupId(UUID.randomUUID())
                .appId(savedApp.getId())
                .tenantId(tenantId)
                .creator(creator)
                .name("Admins Group")
                .desc("group for adminRole")
                .roles(List.of(adminRole))
                .groupType(Group.GroupType.STAFF)
                .build();

        Group superAdminGroup = Group.builder()
                .groupId(UUID.randomUUID())
                .appId(savedApp.getId())
                .tenantId(tenantId)
                .creator(creator)
                .name("Admins Group")
                .desc("group for super adminRole")
                .roles(List.of(superAdminRole, adminRole, developerRole))
                .groupType(Group.GroupType.STAFF)
                .build();

        groupService.saveAll(Set.of(developerGroup, adminGroup, superAdminGroup));

        GroupMember member = new GroupMember(developer);
        superAdminGroup.addMember(member);

        groupService.save(superAdminGroup);
        groupService.save(member);

        return savedApp;

    }




    public Application updateGeneralInfo(UserId userId, UUID appId, GeneralAppInfo appInfo)
            throws ApplicationNotFoundException, UserActionNotAllowedException {

        Application application = getById(appId);

        if (application.isCreator(userId)){

            return updateGeneralInfo(appInfo, application);
        }
        else {

            List<GroupMember> members = groupService.getGroupMembers(appId, userId)
                    .stream()
                    .filter(groupMember -> groupMember.getMemberType() == Group.GroupType.STAFF)
                    .toList();

            if (!isDeveloper(application, members)){

                throw new UserActionNotAllowedException();
            }
        }

        return updateGeneralInfo(appInfo, application);
    }




    private Application updateGeneralInfo(GeneralAppInfo appInfo, Application application) {

        application.setInfo(appInfo);
        return applicationService.save(application);
    }


    public static Boolean isDeveloper(Application application, List<GroupMember> members) {

        List<GroupMember> staffMembers = members
                .stream()
                .filter(groupMember -> groupMember.getMemberType() == Group.GroupType.STAFF)
                .toList();

        List<Group> groups = application.getGroups()
                .stream()
                .filter(Group::isDeveloperGroup)
                .toList();

        for (GroupMember member : staffMembers) {

            if (groups.stream().anyMatch(group -> group.getMembers().contains(member.getId()))) {

                return true;
            }
        }

        return false;
    }


    public List<Application> getDeveloperApplication(UserId userId) {

        List<UUID> apps = groupService.getGroupMembers(userId)
                .stream()
                .filter(groupMember -> groupMember.getMemberType() == Group.GroupType.STAFF)
                .map(GroupMember::getAppId)
                .toList();

        return applicationService.getAllByIds(apps);
    }



    public List<Group> getAppGroups(UUID appId) {

        return applicationService.getById(appId)
                .map(Application::getGroups)
                .orElse(List.of());
    }


}
