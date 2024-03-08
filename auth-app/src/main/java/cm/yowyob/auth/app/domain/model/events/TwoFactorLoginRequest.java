package cm.yowyob.auth.app.domain.model.events;



import java.util.UUID;

public class TwoFactorLoginRequest extends BaseLoginRequest {
    public String code;
    public boolean trustComputer;
    public String twoFactorId;
    public UUID userId;

    public TwoFactorLoginRequest() {
    }

    public TwoFactorLoginRequest(UUID var1, String var2, String var3) {
        this.applicationId = var1;
        this.code = var2;
        this.twoFactorId = var3;
    }

    public TwoFactorLoginRequest(EventInfo var1, UUID var2, String var3, String var4) {
        super(var1);
        this.applicationId = var2;
        this.code = var3;
        this.twoFactorId = var4;

    }


}