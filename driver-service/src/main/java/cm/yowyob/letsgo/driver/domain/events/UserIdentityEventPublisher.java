package cm.yowyob.letsgo.driver.domain.events;


import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;



public interface UserIdentityEventPublisher {

    void publishForEvaluation(HumanIdentity driverLicence, String targetId, String ownerId);
}
