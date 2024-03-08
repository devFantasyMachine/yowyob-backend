package cm.yowyob.auth.app.domain.model.tenant;




import java.util.EnumSet;


public enum LoginMethod {

    SOCIAL_LOGIN,
    EMAIL_PASSWORD,
    PHONE_PASSWORD,
    USERNAME_PASSWORD,
    PHONE_OTP;


    public static final EnumSet<LoginMethod> NON_VERIFIABLE_METHODS
            = EnumSet.of(USERNAME_PASSWORD, SOCIAL_LOGIN);

    public static final EnumSet<LoginMethod> EMAIL_METHODS = EnumSet.of(EMAIL_PASSWORD);

    public static final EnumSet<LoginMethod> PHONE_METHODS = EnumSet.of(PHONE_PASSWORD, PHONE_OTP);

    public static final EnumSet<LoginMethod> PASSWORD_METHODS = EnumSet.of(EMAIL_PASSWORD,
            PHONE_PASSWORD,
            USERNAME_PASSWORD);

    public boolean isPasswordMethod(){

        return PASSWORD_METHODS.contains(this);
    }


    public boolean isPhoneMethod() {
        return PHONE_METHODS.contains(this);
    }

    public boolean isEmailMethod() {
        return EMAIL_METHODS.contains(this);
    }


    public boolean nonRequireVerification() {
        return NON_VERIFIABLE_METHODS.contains(this);
    }
}

