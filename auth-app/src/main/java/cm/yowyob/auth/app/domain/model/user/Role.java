/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.domain.model.user;



import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Getter
@Builder
@AllArgsConstructor
public class Role {

    public static final String SUPER_ADMIN_ROLE_NAME = "SUPER_ADMIN";
    public static final String USER_ROLE_NAME = "USER";
    public static final String DEVELOPER_ROLE_NAME = "DEVELOPER";
    public static final String ADMIN_ROLE_NAME = "ADMIN";

    public static final Role SUPER_ADMIN =
            new Role(Tenant.DEFAULT_TENANT_ID, Role.SUPER_ADMIN_ROLE_NAME, null, null, null);

    public static final Role USER = new Role(Tenant.DEFAULT_TENANT_ID, Role.USER_ROLE_NAME, null, null, null);
    public static final Role DEVELOPER = new Role(Tenant.DEFAULT_TENANT_ID, Role.DEVELOPER_ROLE_NAME, null, null, null);
    public static final Role ADMIN = new Role(Tenant.DEFAULT_TENANT_ID, Role.ADMIN_ROLE_NAME, null, null, null);


    public static Set<Role> SIMPLE_USER_ROLES = Set.of(USER);
    public static Set<Role> DEVELOPER_ROLES = Set.of(USER, DEVELOPER);
    public static Set<Role> ADMIN_ROLES = Set.of(USER, DEVELOPER, ADMIN);
    public static Set<Role> SUPER_ADMIN_ROLES = Set.of(USER, DEVELOPER, ADMIN, SUPER_ADMIN);


    @NonNull
    private final UUID tenantId;
    @NonNull
    private final String name;
    private String desc;
    private final LocalDateTime createdAt;
    private final User creator;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return Objects.equals(tenantId, role.tenantId) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, name);
    }

    @Override
    public String toString() {
        return "Role[" +
                "roleName=" + name + ", " +
                "description=" + desc + ", " +
                "createdAt=" + createdAt + ']';
    }


}
