/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.domain.model.user;


import cm.yowyob.auth.app.domain.model.application.ApplicationRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Getter
@Builder
@AllArgsConstructor
public class Group {

    @NonNull
    private final UUID groupId;
    @NonNull
    private final UUID tenantId;
    private final UUID appId;
    @NonNull
    private final String name;
    private final String desc;

    private final UserId creator;

    private ZonedDateTime insertAt;
    private ZonedDateTime updateAt;


    @Builder.Default
    private List<UUID> members = new ArrayList<>();

    @NonNull
    @Builder.Default
    private GroupType groupType = GroupType.USER;


    // roles by user
    @NonNull
    @Getter
    @Builder.Default
    private List<ApplicationRole> roles = new ArrayList<>();


    public void addMember(GroupMember groupMember) {
        groupMember.accept(this);

        if (members == null)
            members = new ArrayList<>();

        this.members = new ArrayList<>(members);
        this.members.add(groupMember.getId());
    }


    @Getter
    public enum GroupType {

        STAFF("STAFF"), USER("USER");

        private final String type;
        GroupType(String type) {
            this.type = type;
        }
    }

    public List<String> getRolesAsStringList(){

        return roles.stream()
                .map(ApplicationRole::getName)
                .collect(Collectors.toList());

    }


    public boolean isDeveloperGroup() {

        return groupType == GroupType.STAFF && roles
                .stream()
                .map(ApplicationRole::getName)
                .anyMatch(name -> name.contains("DEVELOPER_"));
    }



}


