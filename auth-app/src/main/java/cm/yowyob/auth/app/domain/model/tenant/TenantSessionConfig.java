package cm.yowyob.auth.app.domain.model.tenant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Objects;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantSessionConfig {

    @Builder.Default
    private Duration sessionDuration = Duration.ofDays(10);

    @Builder.Default
    private Duration rememberTokenDuration = Duration.ofDays(120);

    private Boolean useRememberToken = false;


    public Duration getSessionDuration() {
        return this.sessionDuration;
    }

    public Duration getRememberTokenDuration() {
        return this.rememberTokenDuration;
    }

    public Boolean isUseRememberToken() {
        return this.useRememberToken;
    }

    public void setSessionDuration(Duration sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public void setRememberTokenDuration(Duration rememberTokenDuration) {
        this.rememberTokenDuration = rememberTokenDuration;
    }

    public void setUseRememberToken(boolean useRememberToken) {
        this.useRememberToken = useRememberToken;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TenantSessionConfig other)) return false;
        if (!other.canEqual((Object) this)) return false;
        final Object this$sessionDuration = this.getSessionDuration();
        final Object other$sessionDuration = other.getSessionDuration();
        if (!Objects.equals(this$sessionDuration, other$sessionDuration))
            return false;
        final Object this$rememberTokenDuration = this.getRememberTokenDuration();
        final Object other$rememberTokenDuration = other.getRememberTokenDuration();
        if (!Objects.equals(this$rememberTokenDuration, other$rememberTokenDuration))
            return false;
        return this.isUseRememberToken() == other.isUseRememberToken();
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TenantSessionConfig;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $sessionDuration = this.getSessionDuration();
        result = result * PRIME + ($sessionDuration == null ? 43 : $sessionDuration.hashCode());
        final Object $rememberTokenDuration = this.getRememberTokenDuration();
        result = result * PRIME + ($rememberTokenDuration == null ? 43 : $rememberTokenDuration.hashCode());
        result = result * PRIME + (this.isUseRememberToken() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "TenantSessionConfig(sessionDuration=" + this.getSessionDuration() + ", rememberTokenDuration=" + this.getRememberTokenDuration() + ", useRememberToken=" + this.isUseRememberToken() + ")";
    }
}
