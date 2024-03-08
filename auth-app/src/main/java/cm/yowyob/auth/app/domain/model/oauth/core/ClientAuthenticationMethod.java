package cm.yowyob.auth.app.domain.model.oauth.core;


import lombok.Getter;

import java.util.Objects;
import java.util.Set;


@Getter
public enum ClientAuthenticationMethod {

    CLIENT_SECRET_BASIC("client_secret_basic"),
    CLIENT_SECRET_POST("client_secret_post"),
    CLIENT_SECRET_JWT("client_secret_jwt"),
    PRIVATE_KEY_JWT("private_key_jwt"),
    NONE("none");




    private final String value;


    ClientAuthenticationMethod(String value) {
        Objects.requireNonNull(value, "value cannot be empty");
        this.value = value;
    }



    public static final Set<ClientAuthenticationMethod> ALL = Set.of(values());


    public static ClientAuthenticationMethod from(String value) {
        ClientAuthenticationMethod[] var1 = values();

        for (ClientAuthenticationMethod method : var1) {
            if (method.getValue().equals(value)) {
                return method;
            }
        }

        return CLIENT_SECRET_POST;
    }



}
