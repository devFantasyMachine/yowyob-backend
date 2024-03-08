package cm.yowyob.auth.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatorConfiguration {

    private TOTPAlgorithm algorithm;
    private int codeLength;
    private int timeStep;

    public enum TOTPAlgorithm {
        HmacSHA1,
        HmacSHA256,
        HmacSHA512;

        TOTPAlgorithm() {
        }
    }


}
