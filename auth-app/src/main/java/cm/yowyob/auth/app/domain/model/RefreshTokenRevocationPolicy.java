package cm.yowyob.auth.app.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRevocationPolicy {

    public boolean onLoginPrevented;
    public boolean onMultiFactorEnable;
    public boolean onPasswordChanged;

}
