package cm.yowyob.letsgo.trip.mappers;

import cm.yowyob.letsgo.trip.domain.model.plan.stops.LetsGoCoordinate;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.StopLocationEntity;

import java.time.ZoneId;

public class StopLocationMapper extends Mapper<StopLocation, StopLocationEntity>{

    @Override
    public StopLocation toObject(StopLocationEntity entity) {
        return entity == null ? null : StopLocation.builder()
                .city(entity.getCity())
                .name(entity.getName())
                .timezone(entity.getTimezone() == null ? null : ZoneId.of(entity.getTimezone()))
                .coordinate(new LetsGoCoordinate(entity.getLat(), entity.getLon()))
                .build();
    }

    @Override
    public StopLocationEntity toEntity(StopLocation object) {
        return object == null ? null : StopLocationEntity.builder()
                .city(object.city())
                .name(object.name())
                .timezone(object.timezone() == null ? null : object.timezone().getId())
                .lat(object.coordinate() == null ? null : object.coordinate().latitude())
                .lon(object.coordinate() == null ? null : object.coordinate().longitude())
                .build();
    }

}
