package cm.yowyob.letsgo.driver.domain.ports;

import cm.yowyob.letsgo.driver.domain.model.driver.Driver;

import java.util.Optional;


public interface DriverService {
    boolean existsById(String userId);

    Driver save(Driver driver);

    Optional<Driver> getById(String userId);
}
