package cm.yowyob.letsgo.driver.domain.ports;

import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;

public interface HumanIdentityEventPublisher {
    void publishForEvaluation(HumanIdentity humanIdentity, String driverId);
}
