package cm.yowyob.letsgo.driver.domain.model;



/**
 *  contact type. EMAIL | PHONE | URL |
 */
public enum ContactType {

    EMAIL("EMAIL"),
    PHONE("PHONE"),
    URL("URL");

    private final String value;

    ContactType(String typeName){
        this.value = typeName;
    }

    public String getValue() {
        return value;
    }
}
