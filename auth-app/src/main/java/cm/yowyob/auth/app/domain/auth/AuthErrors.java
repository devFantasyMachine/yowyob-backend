package cm.yowyob.auth.app.domain.auth;

public abstract class AuthErrors {

    public static final String INVITATION_NOT_FOUND = "INVITATION_NOT_FOUND";
    public static final String CHALLENGE_NOT_VALID = "CHALLENGE_NOT_VALID";
    public static final String INVITATION_EXPIRED = "INVITATION_EXPIRED";
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String TENANT_NOT_FOUND = "USER_NOT_FOUND";
    public static final String INVALID_TENANT = "INVALID_TENANT";


    public static final String FATAL_ERROR = "FATAL_ERROR";
    public static final String REGISTRATION_NOT_FOUND = "REGISTRATION_NOT_FOUND";
    public static final String LOCKED_DEVICE = "LOCKED_DEVICE";
}
