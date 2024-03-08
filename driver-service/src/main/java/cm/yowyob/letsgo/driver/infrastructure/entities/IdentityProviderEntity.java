package cm.yowyob.letsgo.driver.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("identity_providers")
public class IdentityProviderEntity {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0, name = "provider_id")
    private UUID providerId;

    @Indexed
    private String providerName;

    private String providerDescription;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 0, name = "provider_country")
    private String providerCountry;

    private String providerLocation;
    private String providerCreatorId;
    private Boolean isTrusted;
    private LocalDateTime isTrustedAt;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;

}
