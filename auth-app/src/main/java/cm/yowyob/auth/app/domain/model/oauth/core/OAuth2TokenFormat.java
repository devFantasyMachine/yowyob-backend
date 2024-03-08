package cm.yowyob.auth.app.domain.model.oauth.core;



import lombok.Getter;

import java.io.Serializable;


@Getter
public enum OAuth2TokenFormat implements Serializable {

    SELF_CONTAINED("self-contained"),
    REFERENCE("reference");


    private final String value;

    OAuth2TokenFormat(String value) {
        this.value = value;
    }

    public static OAuth2TokenFormat from(String value) {
        OAuth2TokenFormat[] var1 = values();

        for (OAuth2TokenFormat format : var1) {
            if (format.getValue().equals(value)) {
                return format;
            }
        }

        return SELF_CONTAINED;
    }


}

