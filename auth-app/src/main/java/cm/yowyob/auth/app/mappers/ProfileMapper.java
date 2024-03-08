package cm.yowyob.auth.app.mappers;

import cm.yowyob.auth.app.domain.model.user.Profile;
import cm.yowyob.auth.app.infrastructure.entities.users.ProfileEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;


import java.time.ZoneId;

public class ProfileMapper extends Mapper<Profile, ProfileEntity> {

    private final ContactMapper contactMapper = ContactMapper.getInstance();
    private final AddressMapper addressMapper = AddressMapper.getInstance();


    @Override
    public Profile toObject(ProfileEntity entity) {
        return entity == null ? null : Profile.builder()
                .version(entity.getVersion())
                .avatar(entity.getAvatar())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .about(entity.getAbout())
                .picture(entity.getPicture())
                .birthdate(entity.getBirthdate())
                .timezone(entity.getTimezone() == null ? null : ZoneId.of(entity.getTimezone()))
                .contacts(contactMapper.toObjectSet(entity.getContacts()))
                .address(addressMapper.toObjectSet(entity.getAddress()))
                .build();
    }


    @Override
    public ProfileEntity toEntity(Profile object) {
        return object == null ? null : ProfileEntity.builder()
                .version(object.getVersion())
                .avatar(object.getAvatar())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .gender(object.getGender())
                .about(object.getAbout())
                .picture(object.getPicture())
                .birthdate(object.getBirthdate())
                .timezone(object.getTimezone() == null ? null : String.valueOf(object.getTimezone()))
                .contacts(contactMapper.toEntitySet(object.getContacts()))
                .address(addressMapper.toEntitySet(object.getAddress()))
                .build();
    }


}
