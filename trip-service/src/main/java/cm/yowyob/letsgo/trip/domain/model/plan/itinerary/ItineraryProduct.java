package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;


import cm.yowyob.letsgo.trip.domain.exceptions.IllegalTripResourceUpdateException;
import cm.yowyob.letsgo.trip.domain.exceptions.InvalidReservationRequestException;
import cm.yowyob.letsgo.trip.domain.exceptions.NoSushResourceAvailableException;
import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.resources.ResourceStatus;
import cm.yowyob.letsgo.trip.domain.model.resources.ResourceType;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;




@Data
@Builder
@AllArgsConstructor
public class ItineraryProduct implements Comparable<ItineraryProduct> {

    private UUID productId;
    private UUID planId;
    private final TripResource resource;
    private final Stop fromLocation;
    private final Stop toLocation;
    private final ZonedDateTime departure;
    private final ZonedDateTime arrival;

    private ResourceStatus status;
    private Float cost;
    private Float quantity;

    private TripType tripType;
    private TripPrestige tripPrestige;
    private ServiceType serviceType;



    public Integer getGroupCode(){

        return phy(fromLocation.stopPos(), toLocation.stopPos());
    }



    public static Integer phy(Integer p, Integer q){

        return (int) (Math.pow(2, p) * (2*q + 1))  - 1;
    }


    public ItineraryProduct(UUID productId,
                            TripResource resource,
                            Stop fromLocation,
                            Stop toLocation,
                            ZonedDateTime departure,
                            ZonedDateTime arrival,
                            UUID planId) {

        this.productId = productId;
        this.resource = Objects.requireNonNull(resource);

        this.fromLocation = Objects.requireNonNull(fromLocation);
        this.toLocation = Objects.requireNonNull(toLocation);

        if (fromLocation.stopPos() >= toLocation.stopPos())
            throw new IllegalArgumentException();

        this.departure = departure;
        this.arrival = arrival;
        this.planId = planId;
    }

    public void lock(Float quantity) throws InvalidReservationRequestException {

        if (status == ResourceStatus.CONFIRMED || this.quantity < quantity)
            throw new InvalidReservationRequestException();

        this.quantity = this.quantity - quantity;

        // if quantity is null
        if (Math.abs(this.quantity) <=  Math.pow(10, -4)){

            this.setStatus(ResourceStatus.RESERVED);
        }
        else {

            this.setStatus(ResourceStatus.PARTIALLY_RESERVED);
        }

    }


    public Boolean isAvailable() {

        if (this.quantity == null)
            return false;

        return this.quantity > 0F;
    }



    public boolean interSect(ItineraryProduct anotherProduct){

        return !((fromLocation.stopPos() < anotherProduct.fromLocation.stopPos()  &&
                toLocation.stopPos() <= anotherProduct.fromLocation.stopPos()) ||
                (fromLocation.stopPos() >= anotherProduct.toLocation.stopPos()  &&
                        toLocation.stopPos() > anotherProduct.toLocation.stopPos()));
    }




    public void markConfirmed() throws IllegalTripResourceUpdateException{


    }



    public boolean isConfirmable() {
        return false;
    }

    public void freeUpResource(Float quantity) {

    }

    void increaseCost(float overflowCost) throws IllegalTripResourceUpdateException {
        throw new UnsupportedOperationException();
    }

    public String getLabel() {
        return resource.getLabel();
    }

    public ResourceType getType() {
        return resource.getType();
    }

    @Override
    public int compareTo(ItineraryProduct o) {
        return fromLocation.compareTo(o.fromLocation);
    }

}
