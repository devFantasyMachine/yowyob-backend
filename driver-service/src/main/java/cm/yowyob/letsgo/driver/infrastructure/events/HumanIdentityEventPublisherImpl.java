package cm.yowyob.letsgo.driver.infrastructure.events;

import cm.yowyob.letsgo.driver.domain.events.HumanIdentityEvent;
import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.domain.ports.HumanIdentityEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;




@Component
public class HumanIdentityEventPublisherImpl implements HumanIdentityEventPublisher {


    @Autowired
    ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void publishForEvaluation(HumanIdentity humanIdentity, String driverId) {

        HumanIdentityEvent humanIdentityEvent = HumanIdentityEvent.builder()
                .humanIdentity(humanIdentity)
                .ownerId(driverId)
                .targetId(driverId)
                .build();

        LetsgoEventWrapper letsgoEventWrapper =
                new LetsgoEventWrapper(this, humanIdentityEvent);

        applicationEventPublisher.publishEvent(letsgoEventWrapper);

    }
}
