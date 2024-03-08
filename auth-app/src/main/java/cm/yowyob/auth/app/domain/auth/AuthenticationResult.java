package cm.yowyob.auth.app.domain.auth;

import cm.yowyob.auth.app.domain.model.user.RequireAction;
import cm.yowyob.auth.app.domain.model.user.User;
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
public class AuthenticationResult {

    private User user;
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


    public static AuthenticationResultBuilder from(AuthenticationResult result) {

        return AuthenticationResult.builder()
                .user(result.user)
                .redirectUri(result.redirectUri)
                .sessionToken(result.sessionToken)
                .sessionTokenExpireAt(result.sessionTokenExpireAt)
                .rememberToken(result.rememberToken)
                .rememberTokenExpireAt(result.rememberTokenExpireAt)
                .sessionTokenIssuedAt(result.sessionTokenIssuedAt)
                .requireAction(result.requireAction)
                .verificationId(result.verificationId)
                ;
    }
}

