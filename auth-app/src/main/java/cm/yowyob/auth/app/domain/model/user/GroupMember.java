package cm.yowyob.auth.app.domain.model.user;


import lombok.*;

import java.time.ZonedDateTime;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    private UUID id;
    private UUID groupId;
    private UUID appId;
    private User user;
    private ZonedDateTime addAt;
    private Group.GroupType memberType;



    public GroupMember(User user) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.addAt = ZonedDateTime.now();
    }

    protected void accept(@NonNull Group group){
        this.memberType = group.getGroupType();
        this.groupId = group.getGroupId();
        this.appId = group.getAppId();
    }


}

