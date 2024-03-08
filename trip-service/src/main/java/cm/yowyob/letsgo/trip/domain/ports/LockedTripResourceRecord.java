package cm.yowyob.letsgo.trip.domain.ports;

import cm.yowyob.letsgo.trip.domain.model.reservation.LockedTripResources;

import java.util.Optional;
import java.util.UUID;

public interface LockedTripResourceRecord {
    Optional<LockedTripResources> getByRequestId(UUID requestId);
}
