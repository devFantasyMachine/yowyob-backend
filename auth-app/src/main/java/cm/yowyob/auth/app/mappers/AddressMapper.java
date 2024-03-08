package cm.yowyob.auth.app.mappers;


import cm.yowyob.auth.app.domain.model.user.Address;
import cm.yowyob.auth.app.infrastructure.entities.users.AddressEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;

public class AddressMapper extends Mapper<Address, AddressEntity>{


    private static AddressMapper addressMapper;

    public static AddressMapper getInstance() {

        if (addressMapper == null)
            addressMapper = new AddressMapper();

        return addressMapper;
    }

    @Override
    public Address toObject(AddressEntity addressEntity) {

        if (addressEntity == null) return Address.UNKNOWN;

        return Address.builder()
                .country(addressEntity.getCountry())
                .city(addressEntity.getTown())
                .region(addressEntity.getState())
                .street(addressEntity.getStreet())
                .timezone(addressEntity.getTimezone())
                .zipcode(addressEntity.getPostalCode())
                .build();
    }

    @Override
    public AddressEntity toEntity(Address address) {

        if (address == null  || address.equals(Address.UNKNOWN)) return null;

        return AddressEntity.builder()
                .country(address.getCountry())
                .town(address.getCity())
                .state(address.getRegion())
                .street(address.getStreet())
                .timezone(address.getTimezone())
                .postalCode(address.getZipcode())
                .region(address.getRegion())
                .build();
    }


}
