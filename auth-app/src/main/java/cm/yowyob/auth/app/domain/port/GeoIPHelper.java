package cm.yowyob.auth.app.domain.port;

import cm.yowyob.auth.app.domain.model.user.Address;

import java.util.Optional;

public interface GeoIPHelper {

    Optional<Address> findLocation(String ip);

}
