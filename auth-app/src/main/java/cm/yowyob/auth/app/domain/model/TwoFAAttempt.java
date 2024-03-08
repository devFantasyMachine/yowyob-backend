package cm.yowyob.auth.app.domain.model;


import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.user.UserId;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwoFAAttempt {

    @NonNull
    private UserId userId;
    @NonNull
    private Contact contact;
    private UUID id;
    private String code;
    private Instant issueAt;

    @NonNull
    private Instant expireAt;
    private Boolean isNonUsed;
    private Boolean isAccepted;
    private Instant acceptedAt;

    public boolean isExpired() {
        return Instant.now().isAfter(expireAt);
    }

    public boolean isNonUsed() {
        return this.isNonUsed;
    }

    public void refuse() {

        this.isAccepted = false;
        this.isNonUsed = false;
    }

    public void accept() {

        this.isAccepted =true;
        this.acceptedAt = Instant.now();
        this.isNonUsed = false;
    }

}
