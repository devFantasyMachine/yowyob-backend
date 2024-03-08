package cm.yowyob.auth.app.domain.model.user;


import lombok.Getter;

@Getter
public enum RequireAction {

    CHANGE_PASSWORD("CHANGE_PASSWORD"),
    VALIDATE_REGISTRATION("VALIDATE_REGISTRATION"),
    EMAIL_VERIFICATION("EMAIL_VERIFICATION"),
    VALIDATE_2FA("VALIDATE_2FA"),
    CONFIGURE_2FA("CONFIGURE_2FA"),
    PHONE_NUMBER_VERIFICATION("PHONE_NUMBER_VERIFICATION"),
    RESET_PASSWORD("RESET_PASSWORD"),
    INVALIDATE_SESSION("INVALIDATE_SESSION");


    private final String action;
    RequireAction(String action){
        this.action = action;
    }

}

