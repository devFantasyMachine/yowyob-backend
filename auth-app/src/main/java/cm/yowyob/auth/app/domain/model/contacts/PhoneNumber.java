package cm.yowyob.auth.app.domain.model.contacts;


import java.time.Instant;
import java.time.LocalDateTime;

public final class PhoneNumber extends Contact {

    public static final PhoneNumber NONE = new PhoneNumber("+000 000000000");

    PhoneNumber(String value) {
        super(value, ContactType.PHONE);
    }

    PhoneNumber(String value, boolean isVerified, Instant createdAt, LocalDateTime isVerifiedAt) {
        super(value, isVerified, isVerifiedAt, createdAt, ContactType.PHONE);
    }

    public String getCountryCode(){return  null;}

}
