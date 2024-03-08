package cm.yowyob.letsgo.trip.domain.model.plan.core;


import cm.yowyob.letsgo.trip.domain.model.policies.CancellationPolicy;
import cm.yowyob.letsgo.trip.domain.model.policies.ConfirmationPolicy;
import cm.yowyob.letsgo.trip.domain.model.policies.CoursePolicy;
import cm.yowyob.letsgo.trip.domain.model.policies.ReservationPolicy;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.Map;



@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlannerPlanRequest extends PlanRequest {


    private Map<Integer, String> vehicleIdByLeg;
    private Map<Integer, String> driverIdByLeg;

    private CoursePolicy coursePolicy;
    private ReservationPolicy reservationPolicy;
    private ConfirmationPolicy confirmationPolicy;
    private CancellationPolicy cancellationPolicy;


    public boolean hasDrivers() {
        return driverIdByLeg != null && !driverIdByLeg.isEmpty();
    }

    public boolean hasVehicles() {
        return vehicleIdByLeg != null && !vehicleIdByLeg.isEmpty();
    }


    public Collection<String> getDriverIds() {
        return driverIdByLeg.values();
    }

    public Collection<String> getVehicleIds() {
        return vehicleIdByLeg.values();
    }



}
