package cm.yowyob.auth.app.infrastructure.entities.users;


import cm.yowyob.auth.app.domain.model.user.UserState;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserStateEntity {

    @Id
    private UUID stateId;

    private UserState.State state;
    private UserState.StateReason reason;
    private LocalDateTime instant;
    private String stateAuthorId;
}
