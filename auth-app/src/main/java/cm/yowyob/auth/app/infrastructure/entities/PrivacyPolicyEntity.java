package cm.yowyob.auth.app.infrastructure.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PrivacyPolicyEntity {

    private Integer version;
    private String txt;
    private String privacyPolicyUrl;
}
