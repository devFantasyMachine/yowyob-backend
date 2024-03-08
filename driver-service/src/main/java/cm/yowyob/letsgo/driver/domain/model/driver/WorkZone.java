package cm.yowyob.letsgo.driver.domain.model.driver;


import lombok.*;

import java.util.Set;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkZone {

    private Set<String> cities;
    private String country;
}
