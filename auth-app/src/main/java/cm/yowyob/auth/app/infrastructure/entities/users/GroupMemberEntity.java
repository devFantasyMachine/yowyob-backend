package cm.yowyob.auth.app.infrastructure.entities.users;


import cm.yowyob.auth.app.domain.model.user.Group;
import cm.yowyob.auth.app.domain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GroupMemberEntity {

    @Id
    private UUID id;

    private UUID groupId;

    @ManyToOne(fetch = FetchType.EAGER)
/*    @JoinColumns({
            @JoinColumn(name="user_id", referencedColumnName="userId"),
            @JoinColumn(name="tenant_id", referencedColumnName="tenantId")
    })*/
    private UserEntity user;

    private UUID appId;

    private ZonedDateTime addAt;

    private Group.GroupType memberType;


}
