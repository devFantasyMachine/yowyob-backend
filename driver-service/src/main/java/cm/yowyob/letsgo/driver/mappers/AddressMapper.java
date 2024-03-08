package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.Address;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.AddressEntity;

public class AddressMapper extends Mapper<Address, AddressEntity>{

    @Override
    public Address toObject(AddressEntity entity) {

        if (entity == null)
            return null;

        return Address.builder()
                .addAt(entity.getAddAt())
                .lat(entity.getLat())
                .lon(entity.getLon())
                .state(entity.getState())
                .region(entity.getRegion())
                .town(entity.getTown())
                .street(entity.getStreet())
                .continent(entity.getContinent())
                .isCurrent(entity.getIsCurrent())
                .timezone(entity.getTimezone())
                .country(entity.getCountry())
                .postalCode(entity.getPostalCode())
                .build();
    }

    @Override
    public AddressEntity toEntity(Address object) {

        if (object == null)
            return null;

        return AddressEntity.builder()
                .addAt(object.getAddAt())
                .lat(object.getLat())
                .lon(object.getLon())
                .state(object.getState())
                .region(object.getRegion())
                .town(object.getTown())
                .street(object.getStreet())
                .continent(object.getContinent())
                .isCurrent(object.getIsCurrent())
                .timezone(object.getTimezone())
                .country(object.getCountry())
                .postalCode(object.getPostalCode())
                .build();
    }
}
