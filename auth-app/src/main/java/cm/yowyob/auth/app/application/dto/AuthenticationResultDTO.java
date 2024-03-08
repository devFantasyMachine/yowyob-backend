package cm.yowyob.auth.app.application.dto;

import cm.yowyob.auth.app.domain.auth.AuthenticationResult;
import cm.yowyob.auth.app.domain.model.user.RequireAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResultDTO {

    private UserPrivateDTO user;
    private UUID deviceId;
    private Instant sessionTokenIssuedAt;

    private String rememberToken;
    private Instant rememberTokenExpireAt;

    private String sessionToken;
    private Instant sessionTokenExpireAt;

    private String redirectUri;


    // TODO: 25/01/2024 use data

    private String verificationId;

    private RequireAction requireAction;

    public static AuthenticationResultDTO from(AuthenticationResult result) {

        return AuthenticationResultDTO.builder()
                .deviceId(result.getDeviceId())
                .sessionToken(result.getSessionToken())
                .rememberToken(result.getRememberToken())
                .sessionTokenIssuedAt(result.getSessionTokenIssuedAt())
                .sessionTokenExpireAt(result.getSessionTokenExpireAt())
                .rememberTokenExpireAt(result.getRememberTokenExpireAt())
                .redirectUri(result.getRedirectUri())
                .requireAction(result.getRequireAction())
                .verificationId(result.getVerificationId())
                .user(UserPrivateDTO.from(result.getUser()))
                .build();
    }


}
