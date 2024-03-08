package cm.yowyob.auth.app.domain.model.oauth.client;


import cm.yowyob.auth.app.domain.model.oauth.SignatureAlgorithm;
import cm.yowyob.auth.app.domain.model.oauth.core.OAuth2TokenFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;



@Data
@Builder
@AllArgsConstructor
public class TokenSettings {


    @NonNull
    @Builder.Default
    private Duration authorizationCodeTimeToLive = Duration.ofMinutes(5L);


    @NonNull
    @Builder.Default
    private Duration accessTokenTimeToLive = Duration.ofMinutes(5L);

    @NonNull
    @Builder.Default
    private Duration refreshTokenTimeToLive = Duration.ofMinutes(60L);

    @Builder.Default
    private boolean isReuseRefreshTokens = true;

    @NonNull
    @Builder.Default
    private OAuth2TokenFormat accessTokenFormat = OAuth2TokenFormat.SELF_CONTAINED;

    @NonNull
    @Builder.Default
    private SignatureAlgorithm idTokenSignatureAlgorithm = SignatureAlgorithm.RS256;


}
