package cm.yowyob.letsgo.driver.domain.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Permission {

    public static final Permission CREATE_RESOURCE = Permission.builder()
            .name("can-create-resource")
            .build();

    public static final Permission CREATE_VEHICLE = Permission.builder()
            .name("can-create-vehicle")
            .build();

    public static final Permission CREATE_DRIVER = Permission.builder()
            .name("can-create-driver")
            .build();

    public static final Permission CREATE_HOSTESS = Permission.builder()
            .name("can-create-hostess")
            .build();


    private String name;


}
