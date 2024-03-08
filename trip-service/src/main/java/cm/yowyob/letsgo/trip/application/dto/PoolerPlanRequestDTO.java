package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.DraftTrip;
import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PoolerPlanRequestDTO {

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
    private PaymentSettingDTO paymentSetting;


}
