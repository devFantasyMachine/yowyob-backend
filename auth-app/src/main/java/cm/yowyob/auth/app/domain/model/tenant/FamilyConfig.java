package cm.yowyob.auth.app.domain.model.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyConfig {

    @Builder.Default
    private Boolean enabled = false;
    @Builder.Default
    private Boolean allowChildRegistrations = true;
    private Boolean deleteOrphanedAccounts;
    @Builder.Default
    private Integer deleteOrphanedAccountsDays = 30;
    @Builder.Default
    private Integer maximumChildAge = 12;
    @Builder.Default
    private Integer minimumOwnerAge = 21;
}
