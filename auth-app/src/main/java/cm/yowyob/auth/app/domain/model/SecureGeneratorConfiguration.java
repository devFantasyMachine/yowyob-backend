package cm.yowyob.auth.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecureGeneratorConfiguration {

    private int length;
    private SecureGeneratorType type;

    public enum SecureGeneratorType {
        randomDigits,
        randomBytes,
        randomAlpha,
        randomAlphaNumeric;

        SecureGeneratorType() {
        }
    }

}
