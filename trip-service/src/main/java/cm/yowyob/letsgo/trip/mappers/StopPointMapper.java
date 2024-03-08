package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopPointEntity;

import java.time.Duration;
import java.time.ZoneId;

public class StopPointMapper extends Mapper<Stop, StopPointEntity> {

    @Override
    public Stop toObject(StopPointEntity entity) {
        return entity == null ? null : Stop.builder()
                .withDelay(entity.getDelay() == null ? null : Duration.ofSeconds(entity.getDelay()))
                .withPlace(StopLocation.builder()
                        .city(entity.getCity())
                        .name(entity.getName())
                        .timezone(entity.getTimezone() == null ? null : ZoneId.of(entity.getTimezone()))
                        .coordinate(new LetsGoCoordinate(entity.getLat(), entity.getLon()))
                        .build())
                .withPos(entity.getPos())
                .code(entity.getCode())
                .build();
    }


    @Override
    public StopPointEntity toEntity(Stop object) {

        if (object == null)
            return null;

        StopLocation place = object.place();

        return StopPointEntity.builder()
                .delay(object.delay() == null ? null : object.delay().getSeconds())
                .pos(object.stopPos())
                .code(object.platformCode())
                .city(place == null ? null : place.city())
                .name(place == null ? null : place.name())
                .timezone(place == null ? null : (place.timezone() == null ? null : place.timezone().getId()))
                .lat(place == null ? null : (place.coordinate() == null ? null : place.coordinate().latitude()))
                .lon(place == null ? null : (place.coordinate() == null ? null : place.coordinate().longitude()))
                .build();
    }

}
