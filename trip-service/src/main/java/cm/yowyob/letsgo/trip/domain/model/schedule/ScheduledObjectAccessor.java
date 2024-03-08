package cm.yowyob.letsgo.trip.domain.model.schedule;

import java.util.Optional;

public interface ScheduledObjectAccessor<T extends ScheduledObject>  {

    Optional<T> getScheduledObject(AccessorEntry scheduledObjectId);

    T save(T scheduledObject);

}
