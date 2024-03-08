package cm.yowyob.letsgo.driver.infrastructure.entities;

import cm.yowyob.letsgo.driver.domain.model.resources.LostReason;
import cm.yowyob.letsgo.driver.domain.model.resources.UserResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;


import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@Table("resource_claims")
public class ClaimEntity {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0, name = "claim_id")
    private final UUID claimId;
    
    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1,  name = "resource_id")
    private final String resourceId;

    @Indexed
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 2, name = "user_id")
    private final String userId;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 3, name = "is_active")
    private final Boolean isActive;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 4, name = "resource_type")
    private final UserResourceType userResourceType;


    @Column(value = "take_at")
    private final LocalDateTime takeAt;

    @Column(value = "begin_at")
    private final LocalDateTime beginAt;


    @Column(value = "lost_at")
    private final LocalDateTime lostAt;

    @Column(value = "lost_reason")
    private final LostReason lostReason;

    @Column(value = "claim_duration")
    private final Long claimDuration;

    @Column(value = "claim_type")
    private final Integer claimType;

}
