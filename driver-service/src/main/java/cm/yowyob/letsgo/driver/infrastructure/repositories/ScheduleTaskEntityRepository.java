package cm.yowyob.letsgo.driver.infrastructure.repositories;

import cm.yowyob.letsgo.driver.infrastructure.entities.ScheduleTaskEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ScheduleTaskEntityRepository extends CassandraRepository<ScheduleTaskEntity, UUID> {

    Optional<ScheduleTaskEntity> findByTaskId(UUID taskRequestId);

    Set<ScheduleTaskEntity> findByDriverId(String driverId);
}
