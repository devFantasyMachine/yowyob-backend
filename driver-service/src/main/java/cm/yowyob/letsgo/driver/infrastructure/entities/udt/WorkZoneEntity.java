package cm.yowyob.letsgo.driver.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Set;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("work_zone")
public class WorkZoneEntity {

    private Set<String> cities;
    private String country;
}
