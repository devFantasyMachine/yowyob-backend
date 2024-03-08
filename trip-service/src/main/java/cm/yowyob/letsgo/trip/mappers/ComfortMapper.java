package cm.yowyob.letsgo.trip.mappers;


import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.ComfortEntity;

public class ComfortMapper extends Mapper<Comfort, ComfortEntity>{

    @Override
    public Comfort toObject(ComfortEntity entity) {

        if (entity == null)
            return null;

        return new Comfort(entity.getName(), entity.getDescription());
    }


    @Override
    public ComfortEntity toEntity(Comfort object) {

        if (object == null)
            return null;

        return new ComfortEntity(object.name(), object.description());
    }

}
