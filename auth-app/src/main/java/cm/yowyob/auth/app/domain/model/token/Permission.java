package cm.yowyob.auth.app.domain.model.token;


import java.util.Set;

public enum Permission {


    USER_READ,
    USER_WRITE,

    INVITATION_READ,
    INVITATION_WRITE,

    TENANT_READ,
    TENANT_CREATE,
    TENANT_UPDATE, REGISTRATION_CREATE, AUTH_CREATE;


    public static final Set<Permission> ALL = Set.of(Permission.values());

}
