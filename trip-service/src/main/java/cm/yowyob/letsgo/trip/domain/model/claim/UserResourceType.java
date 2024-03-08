package cm.yowyob.letsgo.trip.domain.model.claim;

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
