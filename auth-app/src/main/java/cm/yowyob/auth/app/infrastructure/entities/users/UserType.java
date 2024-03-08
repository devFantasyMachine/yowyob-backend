/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.infrastructure.entities.users;


import lombok.Getter;

@Getter
public enum UserType {

    USER("USER"),
    ADMIN("ADMIN"),
    DEVELOPER("DEVELOPER");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

}
