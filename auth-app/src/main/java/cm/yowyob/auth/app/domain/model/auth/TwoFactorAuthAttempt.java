package cm.yowyob.auth.app.domain.model.auth;


import cm.yowyob.auth.app.domain.TwoFactorAuthChallenge;



public class TwoFactorAuthAttempt {

    private TwoFactorAuthMetadata metadata;
    private Action action;

    private String id;





    public enum Action {

        TRUST_DEVICE,
        LOCK_DEVICE
    }


}
