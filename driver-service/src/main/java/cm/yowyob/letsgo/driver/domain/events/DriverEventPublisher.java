package cm.yowyob.letsgo.driver.domain.events;

import cm.yowyob.letsgo.driver.domain.model.driver.Driver;

public interface DriverEventPublisher {
    void publishNewDriverEvent(Driver driver);

    void publishUpdateDriverEvent(Driver driver);
}
