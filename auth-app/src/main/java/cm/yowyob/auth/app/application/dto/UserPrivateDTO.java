/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.application.dto;


import cm.yowyob.auth.app.domain.model.user.Profile;
import cm.yowyob.auth.app.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPrivateDTO implements Serializable {

    private String userId;
    private String phone;
    private String username;
    private String email;
    private ProfileDTO profile;

    public static UserPrivateDTO from(User user) {

        return UserPrivateDTO.builder()
                .profile(ProfileDTO.from(user.getProfile()))
                .email(user.getEmail())
                .phone(user.getPhone())
                .username(user.getUsername())
                .userId(user.getUserId().getId())
                .build();
    }


}
