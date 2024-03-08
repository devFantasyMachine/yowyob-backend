package cm.yowyob.letsgo.driver.infrastructure.entities.udt;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("favorite_route")
public class FavoriteRouteEntity {

    private String fromName;
    private String fromCity;
    private String fromTimezone;
    private Double fromLon;
    private Double fromLat;

    private String toName;
    private String toCity;
    private String toTimezone;
    private Double toLon;
    private Double toLat;

    private Set<@Frozen StopArrivalEntity> intermediateStops;


}
