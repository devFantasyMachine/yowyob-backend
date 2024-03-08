package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.infrastructure.entities.users.ContactEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RecoveryChallengeEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ContactEntity> contacts;

}

