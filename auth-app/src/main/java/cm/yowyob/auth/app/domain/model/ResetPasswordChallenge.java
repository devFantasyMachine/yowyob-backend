package cm.yowyob.auth.app.domain.model;


import cm.yowyob.auth.app.domain.model.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;




@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class ResetPasswordChallenge extends TimedChallenge {

    private final User target;

    public ResetPasswordChallenge(String id, UUID tenantId, @NonNull Instant expireAt, User target) {
        super(id, tenantId, expireAt);
        this.target = Objects.requireNonNull(target);
    }

    public ResetPasswordChallenge(String id, UUID tenantId, Instant issueAt, Instant expireAt, Boolean used, Boolean isAccepted, Instant acceptedAt, User target) {
        super(id, tenantId, issueAt, expireAt, used, isAccepted, acceptedAt);
        this.target = Objects.requireNonNull(target);
    }


}
