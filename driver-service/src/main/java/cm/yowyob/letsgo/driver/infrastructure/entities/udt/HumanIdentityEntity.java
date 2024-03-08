package cm.yowyob.letsgo.driver.infrastructure.entities.udt;

import cm.yowyob.letsgo.driver.domain.model.identities.IdentityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("human_identity")
public class HumanIdentityEntity {

    @Column("identity_id")
    private final String identityUId;

    @Column("is_verified")
    private final Boolean isVerified;

    @Column("expire_at")
    private final LocalDateTime expireAt;

    @Column("issue_at")
    private final LocalDateTime issueAt;

    @Column("identity_type")
    private IdentityType type;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("verified_at")
    private LocalDateTime verifiedAt;

    @Column("provider_id")
    private UUID providerId;

    @Column("provider_formatted")
    private String providerFormatted;

    private String details;

    private Set<String> docs;

}
