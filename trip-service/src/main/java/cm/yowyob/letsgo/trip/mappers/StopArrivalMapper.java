package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopArrival;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopArrivalEntity;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopPointEntity;

import java.time.Duration;
import java.time.ZoneId;

public class StopArrivalMapper extends Mapper<StopArrival, StopArrivalEntity> {

    @Override
    public StopArrival toObject(StopArrivalEntity entity) {
        return entity == null ? null : StopArrival.builder()
                .withPlace(StopLocation.builder()
                        .city(entity.getCity())
                        .name(entity.getName())
                        .timezone(entity.getTimezone() == null ? null : ZoneId.of(entity.getTimezone()))
                        .coordinate(new LetsGoCoordinate(entity.getLat(), entity.getLon()))
                        .build())
                .withPos(entity.getPos())
                .delay(entity.getDelay() == null ? null : Duration.ofSeconds(entity.getDelay()))
                .build();
    }


    @Override
    public StopArrivalEntity toEntity(StopArrival object) {

        if (object == null)
            return null;

        StopLocation place = object.place();

        return StopArrivalEntity.builder()
                .pos(object.stopPos())
                .delay(object.delay() == null ? null : object.delay().toSeconds())
                .city(place == null ? null : place.city())
                .name(place == null ? null : place.name())
                .timezone(place == null ? null : (place.timezone() == null ? null : place.timezone().getId()))
                .lat(place == null ? null : (place.coordinate() == null ? null : place.coordinate().longitude()))
                .lat(place == null ? null : (place.coordinate() == null ? null : place.coordinate().longitude()))
                .build();
    }

}
