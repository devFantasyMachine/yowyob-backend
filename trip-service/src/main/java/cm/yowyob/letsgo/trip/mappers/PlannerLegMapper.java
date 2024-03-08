package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.plan.leg.PlannerLeg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopArrival;
import cm.yowyob.letsgo.trip.infrastructure.entities.LegEntity;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.TreeSet;



public class PlannerLegMapper extends Mapper<PlannerLeg, LegEntity>{


    private final StopPointMapper stopPointMapper = new StopPointMapper();
    private final StopArrivalMapper stopArrivalMapper = new StopArrivalMapper();

    private final DriverMapper driverMapper = new DriverMapper();

    private final VehicleMapper vehicleMapper = new VehicleMapper();



    @Override
    public PlannerLeg toObject(LegEntity entity) {

        if (entity == null)
            return null;

        Set<StopArrival> stopArrivals =
                stopArrivalMapper.toObjectSet(entity.getIntermediateStops());

        return new PlannerLeg(
                stopPointMapper.toObject(entity.getFromLocation()),
                stopPointMapper.toObject(entity.getToLocation()),
                stopArrivals == null ? new TreeSet<StopArrival>() : new TreeSet<>(stopArrivals),
                entity.getTransitDuration() == null ? null : Duration.ofSeconds(entity.getTransitDuration()),
                entity.getDistanceMeters(),
                entity.getDirectDistanceMeters(),
                vehicleMapper.toObject(entity.getVehicleInfo()),
                driverMapper.toObject(entity.getDriverInfo()),
                entity.getDeparture() == null ? null : ZonedDateTime.of(entity.getDeparture(), ZoneId.of(entity.getTimezone()) ));

    }


    @Override
    public LegEntity toEntity(PlannerLeg object) {

        if (object == null)
            return null;

        return LegEntity.builder()
                .legIndex(object.getIndex())
                .distanceMeters(object.getDistanceMeters())
                .directDistanceMeters(object.getDirectDistanceMeters())
                .fromLocation(stopPointMapper.toEntity(object.getFromLocation()))
                .toLocation(stopPointMapper.toEntity(object.getToLocation()))
                .driverInfo(driverMapper.toEntity(object.getDriver()))
                .vehicleInfo(vehicleMapper.toEntity(object.getVehicle()))
                .timezone(object.getEstimateDeparture() == null ? null: object.getEstimateDeparture().getZone().getId())
                .departure(object.getEstimateDeparture() == null ? null: object.getEstimateDeparture().withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime())

                .totalDuration(object.getTotalDuration() == null ? null : object.getTotalDuration().getSeconds())
                .transitDuration(object.getTransitDuration() == null ? null : object.getTransitDuration().getSeconds())
                .build();
    }



}
