package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.domain.model.tenant.TenantUsernameConfig;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UsernamePolicyEntity {

    private Integer minUsernameCharacters;
    private Integer maxUsernameCharacters;

    private Boolean withUsernameNumbers;
    private Boolean withUsernameSpecialCharacters; // _, -,
    private Boolean canUsernameStartWithNumber;

    public static UsernamePolicyEntity from(TenantUsernameConfig tenantUsernameConfig) {

        if (tenantUsernameConfig == null)
            return null;

        return UsernamePolicyEntity.builder()
                .maxUsernameCharacters(tenantUsernameConfig.getMaxCharacters())
                .minUsernameCharacters(tenantUsernameConfig.getMinCharacters())
                .canUsernameStartWithNumber(tenantUsernameConfig.getCanStartWithNumber())
                .withUsernameNumbers(tenantUsernameConfig.getWithNumbers())
                .build();
    }


    public TenantUsernameConfig toUsernamePolicy () {

        return TenantUsernameConfig.builder()
                .maxCharacters(maxUsernameCharacters)
                .minCharacters(minUsernameCharacters)
                .canStartWithNumber(canUsernameStartWithNumber)
                .withNumbers(withUsernameNumbers)
                .build();
    }


}

