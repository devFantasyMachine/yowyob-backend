package cm.yowyob.letsgo.driver.domain.model.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class PlannerAttachment {

    private UUID attachmentId;
    private final String plannerId;
    private final String driverId;
    private final LocalDateTime attachedAt;
    private final LocalDateTime detachedAt;
    private final Boolean isAttached;
}

