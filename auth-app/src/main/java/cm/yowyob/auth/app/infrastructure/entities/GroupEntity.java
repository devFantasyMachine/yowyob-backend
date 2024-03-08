package cm.yowyob.auth.app.infrastructure.entities;

import cm.yowyob.auth.app.domain.model.user.Group;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GroupEntity {

    @Id
    private UUID groupId;

    @Column(nullable = false, updatable = false)
    private UUID tenantId;

    @Column(nullable = false, updatable = false)
    private UUID appId;

    @Column(nullable = false)
    private String name;

    private String description;

    private String creator;

    private ZonedDateTime insertAt;
    private ZonedDateTime updateAt;

    private Group.GroupType groupType;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> members;

    // roles by names
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumns(value = {
            @JoinColumn(referencedColumnName = "roleName", updatable = false, nullable = false),
            @JoinColumn(referencedColumnName = "appId",  updatable = false, nullable = false)})
    private List<ApplicationRoleEntity> roles = new ArrayList<>();

}
