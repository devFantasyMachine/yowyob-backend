package cm.yowyob.auth.app.domain.auth;


import cm.yowyob.auth.app.domain.model.device.Device;
import cm.yowyob.auth.app.domain.model.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
public class SessionTokenContext {

    private final User user;
    private final Device device;
    private final boolean isRememberToken;
    private final Instant issuedAt;
    private final Instant expiresAt;
    private final String issuer;


    public SessionTokenContext(User user, Device device, boolean isRememberToken, Instant issuedAt, Instant expiresAt, String issuer) {

        if (issuer == null || issuer.trim().isBlank())
            throw new IllegalArgumentException("issuer cannot be empty");

        this.issuer = issuer;

        if (user == null || device == null)
            throw new IllegalArgumentException("user or device cannot be null");

        if (issuedAt != null && expiresAt != null) {

            if (expiresAt.isBefore(issuedAt))
                throw new IllegalArgumentException("expiresAt must be after issuedAt");
        }

        this.user = user;
        this.device = device;
        this.isRememberToken = isRememberToken;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }
}
