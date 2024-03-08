package cm.yowyob.auth.app.mappers;


import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.infrastructure.entities.users.ContactEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;


public class ContactMapper extends Mapper<Contact, ContactEntity> {

    private static ContactMapper contactMapper;

    public static ContactMapper getInstance() {

        if (contactMapper == null)
            contactMapper = new ContactMapper();

        return contactMapper;
    }

    @Override
    public Contact toObject(ContactEntity entity) {

        if(entity == null)
            return null;

        return Contact.builder()
                .type(entity.getContactType())
                .isVerified(entity.getIsVerified())
                .value(entity.getValue())
                .isVerifiedAt(entity.getIsVerifiedAt())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    public ContactEntity toEntity(Contact object) {

        if(object == null)
            return null;

        return ContactEntity.builder()
                .value(object.getValue())
                .createdAt(object.getCreatedAt())
                .isVerified(object.isVerified())
                .isVerifiedAt(object.getIsVerifiedAt())
                .contactType(object.getType())
                .build();
    }

}
