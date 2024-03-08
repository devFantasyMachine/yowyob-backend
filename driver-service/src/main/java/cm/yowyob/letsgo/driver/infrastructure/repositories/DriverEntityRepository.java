package cm.yowyob.letsgo.driver.infrastructure.repositories;

import cm.yowyob.letsgo.driver.infrastructure.entities.DriverEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface DriverEntityRepository extends CassandraRepository<DriverEntity, String> {

    DriverEntity findByDriverId(String plannerId);

    boolean existsByDriverId(String plannerId);
}
