package cm.yowyob.auth.app.infrastructure.entities.users;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserTwoFactorConfigurationEntity {

    @OneToMany(fetch = FetchType.EAGER)
    private List<TwoFactorAuthChallengeEntity> methods;
    private Boolean tfaEnabled;
}
