package cm.yowyob.letsgo.driver.domain.model.stops;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.SortedSet;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRoute {

    /** The Stop Place where the leg originates. */
    private StopLocation fromLocation;

    /** The Stop Place where the leg begins. */
    private StopLocation toLocation;

    /**
     * Intermediate stops between the Place where the leg originates and the Place
     * where the leg ends.
     */
    private SortedSet<StopArrival> intermediateStops;

}
