package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class DraftTripDTO {

    private UUID draftId;
    private String ownerId;
    @Nonnull
    private StopLocationDTO fromLocation;
    @Nonnull
    private StopLocationDTO toLocation;
    private List<StopDTO> intermediateStops;
    @Nonnull
    private TripType tripType;
    @Nonnull
    private ServiceType serviceType;
    @Nonnull
    private TripPrestige tripPrestige;
    private Set<Comfort> comforts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer usageCount;
}
