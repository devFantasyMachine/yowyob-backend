package cm.yowyob.auth.app.domain.model.oauth.core;



import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

public abstract class AbstractOAuth2Token implements OAuth2Token, Serializable {
    @Serial
    private static final long serialVersionUID = 600L;

    private final String tokenValue;
    private final Instant issuedAt;
    private final Instant expiresAt;

    protected AbstractOAuth2Token(String tokenValue) {
        this(tokenValue, null, null);
    }

    protected AbstractOAuth2Token(String tokenValue, Instant issuedAt, Instant expiresAt) {

        if (tokenValue == null || tokenValue.trim().isEmpty())
            throw new IllegalArgumentException("tokenValue cannot be empty");

        if (issuedAt != null && expiresAt != null) {

            if (expiresAt.isAfter(issuedAt))
                throw new IllegalArgumentException("expiresAt must be after issuedAt");
        }

        this.tokenValue = tokenValue;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public String getTokenValue() {
        return this.tokenValue;
    }

    public Instant getIssuedAt() {
        return this.issuedAt;
    }

    public Instant getExpiresAt() {
        return this.expiresAt;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            AbstractOAuth2Token other = (AbstractOAuth2Token)obj;
            if (!this.getTokenValue().equals(other.getTokenValue())) {
                return false;
            } else {
                if (this.getIssuedAt() != null) {
                    if (this.getIssuedAt().equals(other.getIssuedAt())) {
                        return this.getExpiresAt() != null ? this.getExpiresAt().equals(other.getExpiresAt()) : other.getExpiresAt() == null;
                    }
                } else if (other.getIssuedAt() == null) {
                    return this.getExpiresAt() != null ? this.getExpiresAt().equals(other.getExpiresAt()) : other.getExpiresAt() == null;
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.getTokenValue().hashCode();
        result = 31 * result + (this.getIssuedAt() != null ? this.getIssuedAt().hashCode() : 0);
        result = 31 * result + (this.getExpiresAt() != null ? this.getExpiresAt().hashCode() : 0);
        return result;
    }
}
