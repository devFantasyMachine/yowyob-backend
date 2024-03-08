package cm.yowyob.letsgo.driver.domain.model.identities;


import lombok.Getter;



@Getter
public enum IdentityType {

    VISA("VISA"), CNI("CNI"),
    PERMIT("PERMIT"), SCHOOL_IDENTITY("SCHOOL_IDENTITY"),
    PASSPORT("PASSPORT");

    private final String tag;

    IdentityType(String tag) {
        this.tag = tag;
    }

}
