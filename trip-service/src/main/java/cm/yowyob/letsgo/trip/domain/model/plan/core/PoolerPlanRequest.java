package cm.yowyob.letsgo.trip.domain.model.plan.core;


import cm.yowyob.letsgo.trip.application.dto.PaymentSettingDTO;
import cm.yowyob.letsgo.trip.application.dto.PoolerPlanRequestDTO;
import cm.yowyob.letsgo.trip.domain.model.DraftTrip;
import cm.yowyob.letsgo.trip.domain.model.PaymentSetting;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;


@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PoolerPlanRequest extends PlanRequest {

    private final String vehicleId;
    private final Float totalCost;
    private final Set<TransportMode> transportModes;

    public PoolerPlanRequest(UUID draftId, DraftTrip draftTrip, Float seatCost, Integer seatCount, Float luggageCost, Float luggageBoxQuantity, ZonedDateTime departure, PaymentSetting paymentSetting, String vehicleId, Float totalCost, Set<TransportMode> transportModes) {
        super(draftId, draftTrip, seatCost, seatCount, luggageCost, luggageBoxQuantity, departure, paymentSetting);
        this.vehicleId = vehicleId;
        this.totalCost = totalCost;
        this.transportModes = transportModes;
    }

    public static PoolerPlanRequestBuilder builder() {
        return new PoolerPlanRequestBuilder();
    }

    @Override
    public boolean hasDrivers() {
        return false;
    }

    public boolean hasVehicles() {
        return vehicleId != null;
    }

    @Override
    public Collection<String> getDriverIds() {
        return Set.of();
    }

    @Override
    public Collection<String> getVehicleIds() {
        return vehicleId == null ? Set.of() : Set.of(vehicleId);
    }



    public static class PoolerPlanRequestBuilder {
        private String vehicleId;
        private Float totalCost;
        private Set<TransportMode> transportModes;
        private UUID draftId;
        private DraftTrip draftTrip;
        private Float seatCost;
        private Integer seatCount;
        private Float luggageCost;
        private Float luggageBoxQuantity;
        private ZonedDateTime departure;
        private PaymentSetting paymentSetting;

        PoolerPlanRequestBuilder() {
        }

        public PoolerPlanRequestBuilder vehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public PoolerPlanRequestBuilder totalCost(Float totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public PoolerPlanRequestBuilder transportModes(Set<TransportMode> transportModes) {
            this.transportModes = transportModes;
            return this;
        }

        public PoolerPlanRequestBuilder draftId(UUID draftId) {
            this.draftId = draftId;
            return this;
        }

        public PoolerPlanRequestBuilder draftTrip(DraftTrip draftTrip) {
            this.draftTrip = draftTrip;
            return this;
        }

        public PoolerPlanRequestBuilder seatCost(Float seatCost) {
            this.seatCost = seatCost;
            return this;
        }

        public PoolerPlanRequestBuilder seatCount(Integer seatCount) {
            this.seatCount = seatCount;
            return this;
        }

        public PoolerPlanRequestBuilder luggageCost(Float luggageCost) {
            this.luggageCost = luggageCost;
            return this;
        }

        public PoolerPlanRequestBuilder luggageBoxQuantity(Float luggageBoxQuantity) {
            this.luggageBoxQuantity = luggageBoxQuantity;
            return this;
        }

        public PoolerPlanRequestBuilder departure(ZonedDateTime departure) {
            this.departure = departure;
            return this;
        }

        public PoolerPlanRequestBuilder paymentSetting(PaymentSetting paymentSetting) {
            this.paymentSetting = paymentSetting;
            return this;
        }

        public PoolerPlanRequest build() {
            return new PoolerPlanRequest(this.draftId, this.draftTrip, this.seatCost, this.seatCount, this.luggageCost, this.luggageBoxQuantity, this.departure, this.paymentSetting, this.vehicleId, this.totalCost, this.transportModes);
        }

        public String toString() {
            return "PoolerPlanRequestDTO.PoolerPlanRequestDTOBuilder(vehicleId=" + this.vehicleId + ", totalCost=" + this.totalCost + ", transportModes=" + this.transportModes + ", draftId=" + this.draftId + ", draftTrip=" + this.draftTrip + ", seatCost=" + this.seatCost + ", seatCount=" + this.seatCount + ", luggageCost=" + this.luggageCost + ", luggageBoxQuantity=" + this.luggageBoxQuantity + ", departure=" + this.departure + ", paymentSetting=" + this.paymentSetting + ")";
        }
    }
}
