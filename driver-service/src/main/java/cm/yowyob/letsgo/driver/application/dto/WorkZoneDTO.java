package cm.yowyob.letsgo.driver.application.dto;

import cm.yowyob.letsgo.driver.domain.model.driver.WorkZone;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkZoneDTO {

    private Set<@Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$") String> cities;

    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    private String country;

    public static WorkZoneDTO fromDomainObject(WorkZone workZone) {

        return workZone == null ? null :  WorkZoneDTO.builder()
                .cities(workZone.getCities())
                .country(workZone.getCountry())
                .build();
    }

}
