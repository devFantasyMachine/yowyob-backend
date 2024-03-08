package cm.yowyob.auth.app.domain.model;


import java.util.UUID;

public class JWTConfiguration {

    public UUID accessTokenKeyId;
    public UUID idTokenKeyId;
    public RefreshTokenRevocationPolicy refreshTokenRevocationPolicy;
    public int refreshTokenTimeToLiveInMinutes;
    public Boolean reuseRefreshToken;
    public int accessTokenTimeToLiveInSeconds;

}
