package cm.yowyob.auth.app.domain.model;


import cm.yowyob.auth.app.domain.model.contacts.EmailAddress;
import cm.yowyob.auth.app.domain.model.user.Group;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserId;
import lombok.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;


@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Invitation extends TimedChallenge {

    @NonNull
    private final EmailAddress emailAddress;
    private final User sender;
    private final User target;
    private final InvitationReason reason;
    private final Group group;

    public Invitation(String id, UUID tenantId, @NonNull Instant expireAt, @NonNull EmailAddress emailAddress, User sender, User target, InvitationReason reason, Group group) {
        super(id, tenantId, expireAt);
        this.emailAddress = Objects.requireNonNull(emailAddress);
        this.sender = Objects.requireNonNull(sender);
        this.target = Objects.requireNonNull(target);
        this.reason = Objects.requireNonNull(reason);
        this.group = group;
    }

    public Invitation(String id, UUID tenantId, Instant issueAt, Instant expireAt, Boolean used, Boolean isAccepted, Instant acceptedAt, @NonNull EmailAddress emailAddress, User sender, User target, InvitationReason reason, Group group) {
        super(id, tenantId, issueAt, expireAt, used, isAccepted, acceptedAt);
        this.emailAddress = Objects.requireNonNull(emailAddress);
        this.sender = Objects.requireNonNull(sender);
        this.target = Objects.requireNonNull(target);
        this.reason = Objects.requireNonNull(reason);
        this.group = group;
    }


}
