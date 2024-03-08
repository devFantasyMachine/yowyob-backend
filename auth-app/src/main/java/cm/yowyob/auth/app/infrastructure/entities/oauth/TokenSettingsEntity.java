package cm.yowyob.auth.app.infrastructure.entities.oauth;


import cm.yowyob.auth.app.domain.model.oauth.SignatureAlgorithm;
import cm.yowyob.auth.app.domain.model.oauth.client.TokenSettings;
import cm.yowyob.auth.app.domain.model.oauth.core.OAuth2TokenFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TokenSettingsEntity {

    @Column(nullable = false)
    private Long authorizationCodeTimeToLive;
    @Column(nullable = false)
    private Long accessTokenTimeToLive;
    @Column(nullable = false)
    private Long refreshTokenTimeToLive;
    @Column(nullable = false)
    private Boolean isReuseRefreshTokens;
    @Column(nullable = false)
    private OAuth2TokenFormat accessTokenFormat;
    @Column(nullable = false)
    private SignatureAlgorithm idTokenSignatureAlgorithm;


    public static TokenSettings convert(TokenSettingsEntity settingsEntity) {

        if (settingsEntity == null)
            return null;

        return TokenSettings.builder()
                .accessTokenFormat(settingsEntity.accessTokenFormat)
                .isReuseRefreshTokens(settingsEntity.isReuseRefreshTokens)
                .idTokenSignatureAlgorithm(settingsEntity.idTokenSignatureAlgorithm)
                .authorizationCodeTimeToLive(settingsEntity.authorizationCodeTimeToLive == null ?
                        null : Duration.ofSeconds(settingsEntity.authorizationCodeTimeToLive)
                )
                .accessTokenTimeToLive(settingsEntity.accessTokenTimeToLive == null ?
                        null : Duration.ofSeconds(settingsEntity.accessTokenTimeToLive)
                )
                .refreshTokenTimeToLive(settingsEntity.refreshTokenTimeToLive == null ?
                        null : Duration.ofSeconds(settingsEntity.refreshTokenTimeToLive)
                )
                .build();
    }


    public static TokenSettingsEntity convert(TokenSettings settings) {

        if (settings == null)
            return null;

        return TokenSettingsEntity.builder()
                .accessTokenFormat(settings.getAccessTokenFormat())
                .isReuseRefreshTokens(settings.isReuseRefreshTokens())
                .idTokenSignatureAlgorithm(settings.getIdTokenSignatureAlgorithm())
                .authorizationCodeTimeToLive(settings.getAuthorizationCodeTimeToLive().toSeconds())
                .accessTokenTimeToLive(settings.getAccessTokenTimeToLive().toSeconds())
                .refreshTokenTimeToLive(settings.getRefreshTokenTimeToLive().toSeconds())
                .build();

    }



}
