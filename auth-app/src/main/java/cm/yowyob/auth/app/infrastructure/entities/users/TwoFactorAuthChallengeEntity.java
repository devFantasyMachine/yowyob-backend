package cm.yowyob.auth.app.infrastructure.entities.users;


import cm.yowyob.auth.app.domain.TwoFactorAuthChallenge;
import cm.yowyob.auth.app.domain.model.contacts.Contact;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TwoFactorAuthChallengeEntity {

    @Id
    private String id;
    private Boolean lastUsed;
    private TwoFactorAuthChallenge.TwoFactorMethod method;
    private String secret;
    private String phone;
    private String email;

}
