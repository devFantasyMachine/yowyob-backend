package cm.yowyob.letsgo.driver.domain.model.resources;

public enum UserResourceType {

    DRIVER("driver"),

    VEHICLE("vehicle"),

    HOSTESS("hostess"),
    POOLER_ACTIVITY("activity");

    private final String name;
    UserResourceType(String name) {
        this.name = name;
    }

}
