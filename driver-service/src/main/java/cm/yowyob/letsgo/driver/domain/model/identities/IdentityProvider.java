package cm.yowyob.letsgo.driver.domain.model.identities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class IdentityProvider {

    private UUID providerId;
    private String providerName;
    private String providerDescription;
    private String providerCountry;
    private String providerLocation;
    private String providerCreatorId;
    private Boolean isTrusted;
    private LocalDateTime isTrustedAt;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    private Set<IdentityType> supportedType;

    public IdentityProvider(UUID providerId, String providerName) {
        this.providerId = providerId;
        this.providerName = providerName;
    }

    public IdentityProvider(UUID providerId, String providerName, String providerCountry, String providerLocation) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.providerCountry = providerCountry;
        this.providerLocation = providerLocation;
    }


    public boolean support(IdentityType type) {
        return supportedType != null && supportedType.contains(type);
    }

    public String getFormatted() {

        return providerName + " ( " + providerLocation + " " + providerCountry + " )" ;
    }
}
