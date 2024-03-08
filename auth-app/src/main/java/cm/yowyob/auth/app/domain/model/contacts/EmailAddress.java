package cm.yowyob.auth.app.domain.model.contacts;

import java.time.Instant;
import java.time.LocalDateTime;

public final class EmailAddress extends Contact {

    EmailAddress(String value) {
        super(value, ContactType.EMAIL);
    }

    EmailAddress(String value, boolean isVerified, Instant createdAt, LocalDateTime isVerifiedAt) {
        super(value, isVerified, isVerifiedAt, createdAt, ContactType.EMAIL);
    }
}
