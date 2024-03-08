package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;


import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItinerarySolveContext {

    /**
     * list of intermediates stop points. stopPoints can be nullable
     */
    List<Stop> stops;
    StopLocation fromLocation;
    StopLocation toLocation;

    @Builder.Default
    SolveType solveType = SolveType.MULTIPLE_LEG;

    public enum SolveType {
        SINGLE_LEG,
        MULTIPLE_LEG
    }


}
