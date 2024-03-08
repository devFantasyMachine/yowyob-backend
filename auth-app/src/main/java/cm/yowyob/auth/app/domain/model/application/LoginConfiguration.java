package cm.yowyob.auth.app.domain.model.application;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginConfiguration {

    private boolean allowTokenRefresh;
    private boolean generateRefreshTokens;
}
