package cm.yowyob.letsgo.trip.domain.model.plan.core;

import cm.yowyob.letsgo.trip.domain.model.DraftTrip;
import cm.yowyob.letsgo.trip.domain.model.PaymentSetting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PlanRequest {

    private UUID draftId;
    private DraftTrip draftTrip;

    private Float seatCost;
    private Integer seatCount;
    private Float luggageCost;
    private Float luggageBoxQuantity;

    private ZonedDateTime departure;
    private PaymentSetting paymentSetting;


    public abstract boolean hasDrivers();
    public abstract boolean hasVehicles();

    public final boolean isBasedOnExistingDraftTrip() {
        return draftId != null;
    }

    public boolean hasDraft() {

        return draftTrip != null;
    }

    public boolean hasItinerary() {
        return hasDraft() &&
                (draftTrip.getFromLocation() != null ||
                        draftTrip.getToLocation() != null ||
                        draftTrip.getIntermediateStops() != null);
    }

    public abstract Collection<String> getDriverIds();
    public abstract Collection<String> getVehicleIds();


}
