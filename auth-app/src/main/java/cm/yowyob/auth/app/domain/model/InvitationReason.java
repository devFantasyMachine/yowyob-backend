package cm.yowyob.auth.app.domain.model;


import java.util.EnumSet;

public enum InvitationReason {

    VALIDATE_ADMIN_ACCOUNT, VALIDATE_DEVELOPER_ACCOUNT, JOIN_GROUP;


    public static final EnumSet<InvitationReason> VALIDATE_ACCOUNT_REASONS = EnumSet.of(VALIDATE_ADMIN_ACCOUNT, VALIDATE_DEVELOPER_ACCOUNT);


    public boolean isValidateAccount(){

        return VALIDATE_ACCOUNT_REASONS.contains(this);

    }


}

