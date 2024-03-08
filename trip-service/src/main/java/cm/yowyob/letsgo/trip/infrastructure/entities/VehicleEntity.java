package cm.yowyob.letsgo.trip.infrastructure.entities;



import cm.yowyob.letsgo.trip.domain.model.plan.TransportMode;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.ComfortEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.LuggageBoxEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.ScoreEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.SeatEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("vehicle")
public class VehicleEntity {

    @Column( "vehicle_id")
    private final String vehicleId;

    @Column("owner_id")
    private final String ownerId;

    private final Integer version;
    private final String matriculation;

    @Column(value = "gray_card")
    private final String grayCard;

    @Column(value = "insurancenumber")
    private final String insuranceNumber;

    @Column(value = "insurance_validity_date")
    private final Instant insuranceValidityDate;
    private final String mark;
    private final String model;
    private final String comment;

    @Column(value = "transport_mode")
    private final TransportMode transportMode;

    @Column(value = "place_count")
    private final Integer placeCount;
    private final Set<String> images;

    @Column(value = "luggage_boxes")
    private final Set<LuggageBoxEntity> luggageBoxes;

    private final Set<ComfortEntity> comforts;
    private final Set<SeatEntity> seats;
    private final ScoreEntity score;

    @Column(value = "created_at")
    private final LocalDateTime createdAt;


}



