package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.driver.Subscription;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.SubscriptionEntity;

public class SubscriptionMapper extends Mapper<Subscription, SubscriptionEntity>{
    @Override
    public Subscription toObject(SubscriptionEntity entity) {
        return entity == null ? null : Subscription.builder()
                .subscriptionType(entity.getSubscriptionType())
                .isActive(entity.getIsActive())
                .id(entity.getId())
                .endAt(entity.getEndAt())
                .startedAt(entity.getStartedAt())
                .build();
    }

    @Override
    public SubscriptionEntity toEntity(Subscription object) {
        return object == null ? null : SubscriptionEntity.builder()
                .subscriptionType(object.getSubscriptionType())
                .isActive(object.getIsActive())
                .id(object.getId())
                .endAt(object.getEndAt())
                .startedAt(object.getStartedAt())
                .build();
    }
}
