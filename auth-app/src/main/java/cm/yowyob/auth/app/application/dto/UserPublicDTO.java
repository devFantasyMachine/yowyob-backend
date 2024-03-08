/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.application.dto;


import cm.yowyob.auth.app.domain.model.user.Profile;
import cm.yowyob.auth.app.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Builder
@Data
@AllArgsConstructor
public class UserPublicDTO implements Serializable {

    private final String phone;
    private final String email;
    private final Profile profile;
    private final String username;

    public static UserPublicDTO form(User user) {
        return UserPublicDTO.builder()
                .profile(user.getProfile())
                .username(user.getUsername())
                .email(user.getEmailVerified() ? user.getEmail() : null)
                .phone(user.getPhoneVerified() ? user.getPhone() : null)
                .build();
    }
}
