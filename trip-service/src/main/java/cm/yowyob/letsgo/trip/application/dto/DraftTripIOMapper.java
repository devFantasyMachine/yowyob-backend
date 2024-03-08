package cm.yowyob.letsgo.trip.application.dto;

import cm.yowyob.letsgo.trip.domain.model.DraftTrip;
import cm.yowyob.letsgo.trip.mappers.io.IOMapper;

import java.util.stream.Collectors;

public class DraftTripIOMapper extends IOMapper<DraftTrip, DraftTripDTO> {
    @Override
    public DraftTripDTO toDTO(DraftTrip entity) {

        return entity == null ? null : DraftTripDTO.builder()
                .tripType(entity.getTripType())
                .tripPrestige(entity.getTripPrestige())
                .serviceType(entity.getServiceType())
                .draftId(entity.getDraftId())
                .ownerId(entity.getOwnerId())
                .updatedAt(entity.getUpdatedAt())
                .createdAt(entity.getCreatedAt())
                .usageCount(entity.getUsageCount())

                .intermediateStops(entity.getIntermediateStops() == null ? null : entity.getIntermediateStops()
                        .stream()
                        .map(StopDTO::fromStop)
                        .collect(Collectors.toList())
                )
                .fromLocation(StopLocationDTO.fromStopLocation(entity.getFromLocation()))
                .toLocation(StopLocationDTO.fromStopLocation(entity.getToLocation()))
                .comforts(entity.getComforts())
                .build();

    }

    @Override
    public DraftTrip toObject(DraftTripDTO object) {
        return object == null ? null : DraftTrip.builder()
                .tripType(object.getTripType())
                .tripPrestige(object.getTripPrestige())
                .serviceType(object.getServiceType())

                .intermediateStops(object.getIntermediateStops() == null ? null : object.getIntermediateStops()
                        .stream()
                        .map(StopDTO::toStop)
                        .collect(Collectors.toList()))
                .fromLocation(object.getFromLocation().toStopLocation())
                .toLocation(object.getToLocation().toStopLocation())
                .comforts(object.getComforts())
                .build();
    }

}
