package cm.yowyob.auth.app.domain.model.events;


import java.util.UUID;

public class BaseLoginRequest extends BaseEventRequest {

    public UUID applicationId;
    public boolean newDevice;
    public boolean noJWT;

    public String captchaId;

    public BaseLoginRequest() {
    }

    public BaseLoginRequest(EventInfo var1) {
        super(var1);
    }


}
