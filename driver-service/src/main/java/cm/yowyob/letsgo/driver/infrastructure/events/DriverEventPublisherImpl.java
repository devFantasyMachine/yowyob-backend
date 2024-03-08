package cm.yowyob.letsgo.driver.infrastructure.events;

import cm.yowyob.letsgo.driver.domain.events.DriverEventPublisher;
import cm.yowyob.letsgo.driver.domain.model.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;



@Component
public class DriverEventPublisherImpl implements DriverEventPublisher {


    @Autowired
    ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void publishNewDriverEvent(Driver driver) {

        LetsgoEventWrapper letsgoEventWrapper =
                new LetsgoEventWrapper(this, driver);

        applicationEventPublisher.publishEvent(letsgoEventWrapper);
    }

    @Override
    public void publishUpdateDriverEvent(Driver driver) {

        LetsgoEventWrapper letsgoEventWrapper =
                new LetsgoEventWrapper(this, driver);

        applicationEventPublisher.publishEvent(letsgoEventWrapper);
    }



}
