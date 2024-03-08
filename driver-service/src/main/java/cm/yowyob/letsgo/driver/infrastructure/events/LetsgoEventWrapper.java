package cm.yowyob.letsgo.driver.infrastructure.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class LetsgoEventWrapper extends ApplicationEvent {

    private final Object letsgoEvent;

    public LetsgoEventWrapper(Object source, Object letsgoEvent) {
        super(source);
        this.letsgoEvent = letsgoEvent;
    }

}
