package cm.yowyob.auth.app.domain.model;


import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;


@Getter
public abstract class TimedChallenge {

    private final String id;
    private final UUID tenantId;
    private final Instant issueAt;
    private final Instant expireAt;
    private Boolean used;
    private Boolean accepted;
    private Instant acceptedAt;


    public TimedChallenge(String id, UUID tenantId, @NonNull Instant expireAt) {
        this(
                id,
                tenantId,
                Instant.now(),
                expireAt,
                false,
                false,
                expireAt);

        if (expireAt.isBefore(issueAt))
            throw new IllegalArgumentException();

    }


    public TimedChallenge(String id, UUID tenantId, Instant issueAt, Instant expireAt, Boolean used, Boolean isAccepted, Instant acceptedAt) {
        this.id = Objects.requireNonNull(id);
        this.tenantId = Objects.requireNonNull(tenantId);
        this.issueAt = Objects.requireNonNull(issueAt);
        this.expireAt = Objects.requireNonNull(expireAt);
        this.used = Objects.requireNonNull(used);
        this.accepted = Objects.requireNonNull(isAccepted);
        this.acceptedAt = acceptedAt;


        if (expireAt.isBefore(issueAt))
            throw new IllegalArgumentException();

    }


    public final boolean isExpired() {
        return Instant.now().isAfter(expireAt);
    }

    public final boolean isNonUsed() {
        return !this.used;
    }

    public final void reject() {

        if (used)
            throw new RuntimeException();

        this.accepted = false;
        this.used = true;
    }

    public void accept() {

        if (accepted)
            throw new RuntimeException();

        this.accepted = true;
        this.acceptedAt = Instant.now();
        this.used = true;
    }

    public Duration getExpireDuration(){

        return Duration.between(issueAt, expireAt);
    }



}
