package cm.yowyob.letsgo.driver.domain.model.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class Subscription {


    public static Subscription NONE = Subscription.builder()
            .isActive(true)
            .subscriptionType(SubscriptionType.NONE)
            .amount(0F)
            .build();


    private String driverId;
    private Boolean isActive;
    private UUID id;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
    private SubscriptionType subscriptionType;
    private Float amount;


    public enum SubscriptionType {

        NONE,

        MONTHLY,

        WEEKLY,

        YEARLY


    }





}
