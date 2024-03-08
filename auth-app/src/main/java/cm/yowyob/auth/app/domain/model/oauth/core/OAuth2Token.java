package cm.yowyob.auth.app.domain.model.oauth.core;



import java.time.Instant;

public interface OAuth2Token {

    String getTokenValue();

    default Instant getIssuedAt() {
        return null;
    }

    default Instant getExpiresAt() {
        return null;
    }
}

