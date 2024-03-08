package cm.yowyob.auth.app.domain.model.events;


public abstract class BaseEventRequest {
    public EventInfo eventInfo;

    public BaseEventRequest() {
    }

    public BaseEventRequest(EventInfo var1) {
        this.eventInfo = var1;
    }
}
