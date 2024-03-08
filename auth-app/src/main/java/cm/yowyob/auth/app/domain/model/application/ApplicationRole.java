package cm.yowyob.auth.app.domain.model.application;


import cm.yowyob.auth.app.domain.model.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRole implements Comparable<ApplicationRole> {

    private LocalDateTime addInstant;
    private LocalDateTime lastUpdateInstant;

    @NonNull
    private String name;
    private String description;
    private Boolean isDefault;
    private Boolean isSuperRole;
    private Boolean isBusiness;
    private UUID appId;
    private UUID tenantId;



    public static ApplicationRole createUserRole(Application application) {

        String role = "USER_" + application.getInfo().getShortName().toUpperCase();

        return createRole(true,
                false,
                role,
                "default user role",
                application,
                false);

    }


    public static ApplicationRole createDeveloperRole(Application application) {

        String role = "DEVELOPER_" + application.getInfo().getShortName().toUpperCase();

        return createRole(false,
                false,
                role,
                "default developer role",
                application, false);

    }


    public static ApplicationRole createRole(boolean isDefault,
                                             boolean isSuperRole,
                                             String role,
                                             String description,
                                             Application application,
                                             boolean isBusiness) {

        return ApplicationRole.builder()
                .isDefault(isDefault)
                .isSuperRole(isSuperRole)
                .isBusiness(isBusiness)
                .name(role.toUpperCase())
                .description(description)
                .addInstant(LocalDateTime.now())
                .appId(application.getId())
                .tenantId(application.getTenantId())
                .build();
    }




    public static ApplicationRole createAdminRole(Application application) {

        String role = "ADMIN_" + application.getInfo().getShortName().toUpperCase();

        return createRole(false, false, role, "admin role", application, false);

    }


    public static ApplicationRole createSuperAdminRole(Application application) {

        String role = "SUPER_ADMIN_" + application.getInfo().getShortName().toUpperCase();

        return createRole(false, true, role, "super admin role", application, false);

    }


    public String getDisplay() {
        return this.description != null ? this.description + " (" + this.name + ")" : this.name;
    }


    public int compareTo(ApplicationRole var1) {
        return this.name.compareTo(var1.name);
    }


}
