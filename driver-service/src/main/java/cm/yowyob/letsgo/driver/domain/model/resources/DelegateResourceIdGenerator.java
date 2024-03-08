package cm.yowyob.letsgo.driver.domain.model.resources;


import java.util.HashMap;
import java.util.Map;

public class DelegateResourceIdGenerator implements ResourceIdGenerator {

    private static DelegateResourceIdGenerator delegateResourceIdGenerator;


    private final Map<UserResourceType, ResourceIdGenerator> generators;

    public DelegateResourceIdGenerator() {
        this.generators = new HashMap<>();

        this.generators.put(UserResourceType.DRIVER, new DriverIdGenerator());
        this.generators.put(UserResourceType.HOSTESS, new HostessIdGenerator());
        this.generators.put(UserResourceType.VEHICLE, new VehicleIdGenerator());
    }

    public static DelegateResourceIdGenerator getInstance(){

        if (delegateResourceIdGenerator == null) {
            delegateResourceIdGenerator = new DelegateResourceIdGenerator();
        }

        return delegateResourceIdGenerator;
    }

    @Override
    public String generate(UserResource userResource) {
        ResourceIdGenerator resourceIdGenerator = this.generators.get(userResource.getResourceType());

        if (resourceIdGenerator == null)
            throw new UnsupportedOperationException();

        return resourceIdGenerator.generate(userResource);
    }
}
