package cm.yowyob.letsgo.driver.infrastructure.entities.udt;

import cm.yowyob.letsgo.driver.domain.model.driver.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("subscription")
public class SubscriptionEntity {


    private String driverId;
    private Boolean isActive;
    private UUID id;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
    private Subscription.SubscriptionType subscriptionType;

}
