package cm.yowyob.letsgo.driver.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("driver_licence")
public class DriverLicenceEntity {

    @Column("licence_id")
    private final String licenceId;

    @Column("identity_provider")
    private final String identityProvider;

    @Column("is_verified")
    private final Boolean isVerified;

    @Column("expire_at")
    private final LocalDateTime expireAt;

    @Column("issue_at")
    private final LocalDateTime issueAt;

    private final Set<String> docs;

}
