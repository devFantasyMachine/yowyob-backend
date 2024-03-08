package cm.yowyob.letsgo.trip.mappers;


import cm.yowyob.letsgo.trip.domain.model.vehicle.LuggageBox;
import cm.yowyob.letsgo.trip.infrastructure.entities.udt.LuggageBoxEntity;

public class LuggageBoxMapper extends Mapper<LuggageBox, LuggageBoxEntity>{

    @Override
    public LuggageBox toObject(LuggageBoxEntity entity) {

        if (entity == null)
            return null;

        return new LuggageBox(entity.getBoxLabel(), entity.getBoxNumber(), entity.getBoxQuantityKg());

    }


    @Override
    public LuggageBoxEntity toEntity(LuggageBox object) {

        if (object == null)
            return null;

        return new LuggageBoxEntity(object.getBoxLabel(), object.getBoxNumber(), object.getBoxQuantityKg());

    }
}
