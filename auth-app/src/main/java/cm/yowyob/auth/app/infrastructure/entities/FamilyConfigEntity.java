package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.domain.model.tenant.FamilyConfig;
import jakarta.persistence.Column;
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
public class FamilyConfigEntity {

    @Column(nullable = false)
    private Boolean familyConfigEnabled;
    private Boolean allowChildRegistrations;
    private Boolean deleteOrphanedAccounts;
    private Integer deleteOrphanedAccountsDays;
    private Integer maximumChildAge;
    private Integer minimumOwnerAge;

    public static FamilyConfig to(FamilyConfigEntity familyConfig) {

        if (familyConfig == null)
            return null;

        return FamilyConfig.builder()
                .enabled(familyConfig.getFamilyConfigEnabled())
                .allowChildRegistrations(familyConfig.getAllowChildRegistrations())
                .deleteOrphanedAccounts(familyConfig.getDeleteOrphanedAccounts())
                .deleteOrphanedAccountsDays(familyConfig.getDeleteOrphanedAccountsDays())
                .maximumChildAge(familyConfig.getMaximumChildAge())
                .minimumOwnerAge(familyConfig.getMinimumOwnerAge())
                .build();
    }


    public static FamilyConfigEntity from(FamilyConfig familyConfig) {

        if (familyConfig == null)
            return null;

        return FamilyConfigEntity.builder()
                .familyConfigEnabled(familyConfig.getEnabled())
                .allowChildRegistrations(familyConfig.getAllowChildRegistrations())
                .deleteOrphanedAccounts(familyConfig.getDeleteOrphanedAccounts())
                .deleteOrphanedAccountsDays(familyConfig.getDeleteOrphanedAccountsDays())
                .maximumChildAge(familyConfig.getMaximumChildAge())
                .minimumOwnerAge(familyConfig.getMinimumOwnerAge())
                .build();
    }


}
