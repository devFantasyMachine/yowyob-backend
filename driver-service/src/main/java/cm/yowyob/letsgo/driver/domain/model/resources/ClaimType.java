package cm.yowyob.letsgo.driver.domain.model.resources;

import lombok.Getter;

@Getter
public enum ClaimType {

    ALL("all"),

    INFO_READ_WRITE("read.write"),
    INFO_READ_ONLY("read"),

    PLANNING_READ_WRITE("planning.read.write"),
    PLANNING_READ_ONLY("planning.read"),

    RENT("rent"), NONE("none");


    private final String scope;

    ClaimType(String scope) {
        this.scope = scope;
    }

}
