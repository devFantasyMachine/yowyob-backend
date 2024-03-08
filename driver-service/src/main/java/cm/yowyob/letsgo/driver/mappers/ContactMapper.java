package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.Contact;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.ContactEntity;

public class ContactMapper extends Mapper<Contact, ContactEntity>{

    @Override
    public Contact toObject(ContactEntity entity) {

        if (entity == null)
            return null;

        return Contact.builder()
                .value(entity.getValue())
                .createdAt(entity.getCreatedAt())
                .type(entity.getType())
                .isFavorite(entity.isFavorite())
                .isVerified(entity.isVerified())
                .isVerifiedAt(entity.getIsVerifiedAt())
                .build();
    }

    @Override
    public ContactEntity toEntity(Contact object) {

        if (object  == null)
            return null;

        return ContactEntity.builder()
                .value(object.getValue())
                .createdAt(object.getCreatedAt())
                .type(object.getType())
                .isFavorite(object.isFavorite())
                .isVerified(object.isVerified())
                .isVerifiedAt(object.getIsVerifiedAt())
                .build();
    }
}
