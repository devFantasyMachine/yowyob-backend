package cm.yowyob.auth.app.domain.model.contacts;



import cm.yowyob.auth.app.domain.exceptions.InvalidContactException;
import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public final class ContactFactory {

    private final static Map<ContactType, ContactValidator> validators = new HashMap<>();
    private static ContactFactory contactFactory;

    private ContactFactory() {

        validators.put(ContactType.PHONE, new PhoneValidator());
        validators.put(ContactType.WHATSAPP, new PhoneValidator());
        validators.put(ContactType.EMAIL, new EmailValidator());
    }

    public static ContactFactory getInstance() {

        if (contactFactory == null)
            contactFactory = new ContactFactory();

        return contactFactory;
    }


    /**
     * create contact
     *
     * @param value        the contact value
     * @param type         the contact type
     * @param createdAt    createdAt
     * @param isVerified   isVerified
     * @param isVerifiedAt isVerifiedAt
     * @param isFavorite   isFavorite
     * @return {@link Contact}
     * @throws InvalidContactException cm.yowyob.letsgo.auth.service.domain.exceptions. invalid contact exception
     * @see Contact
     */
    public Contact createContact(@NonNull String value,
                                 @NonNull ContactType type,
                                 Instant createdAt,
                                 boolean isVerified,
                                 LocalDateTime isVerifiedAt,
                                 boolean isFavorite) throws InvalidContactException {

        ContactValidator contactValidator = validators.get(type);

        if (!contactValidator.isValid(value))
            throw new InvalidContactException("value " + value + " doesn't respect clause " + contactValidator.getClause());

        if (type == ContactType.PHONE)
            return new PhoneNumber(value, isVerified, createdAt, isVerifiedAt);

        if (type == ContactType.WHATSAPP)
            return new PhoneNumber(value, isVerified, createdAt, isVerifiedAt);

        if (type == ContactType.EMAIL)
            return new EmailAddress(value, isVerified, createdAt, isVerifiedAt);

        return new Contact(value, isFavorite, isVerifiedAt, createdAt, type);
    }


    /**
     * create contact
     *
     * @param value the contact value
     * @param type  the contact type
     * @return {@link Contact}
     * @throws InvalidContactException cm.yowyob.letsgo.auth.service.domain.exceptions. invalid contact exception
     * @see Contact
     */
    public Contact createContact(String value, ContactType type) throws InvalidContactException {

        return createContact(value, type, Instant.now(), false, null, false);
    }

    public Contact createEmailAddress(String emailAddress) throws InvalidContactException {

        return createContact(emailAddress, ContactType.EMAIL);
    }

    public Contact createPhoneNumber(String value) throws InvalidContactException {

        return createContact(value, ContactType.PHONE);
    }


}
