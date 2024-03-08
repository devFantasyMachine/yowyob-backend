package cm.yowyob.auth.app.infrastructure.entities.tenant;

import cm.yowyob.auth.app.domain.model.RateLimitedRequestConfiguration;
import cm.yowyob.auth.app.domain.model.tenant.TenantRateLimitConfig;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(RateLimitedRequestConfigurationEntity.EntityId.class)
public class RateLimitedRequestConfigurationEntity {

    private Boolean isEnabled;
    private Integer limitCount;
    private Integer timePeriodInSeconds;

    @Id
    private RateLimitedRequestConfiguration.RequestType type;

    @Id
    private TenantRateLimitConfig.RateLimitedRequestType requestType;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EntityId {

        private RateLimitedRequestConfiguration.RequestType type;
        private TenantRateLimitConfig.RateLimitedRequestType requestType;
    }

}
