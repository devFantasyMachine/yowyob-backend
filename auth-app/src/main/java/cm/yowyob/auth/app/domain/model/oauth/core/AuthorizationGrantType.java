package cm.yowyob.auth.app.domain.model.oauth.core;


import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;


@Getter
public enum AuthorizationGrantType implements Serializable {


    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials"),
    PASSWORD("password"),
    JWT_BEARER("urn:ietf:params:oauth:grant-type:jwt-bearer");

    private final String value;

    AuthorizationGrantType(String value) {
        Objects.requireNonNull(value, "value cannot be empty");
        this.value = value;
    }


    public static AuthorizationGrantType from(String value) {
        AuthorizationGrantType[] var1 = values();

        for (AuthorizationGrantType grantType : var1) {
            if (grantType.getValue().equals(value)) {
                return grantType;
            }
        }

        return AUTHORIZATION_CODE;
    }



}
