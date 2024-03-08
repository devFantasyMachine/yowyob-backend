package cm.yowyob.letsgo.driver.domain.ports;

import cm.yowyob.letsgo.driver.domain.model.driver.PlannerAttachment;

import java.util.Optional;

public interface PlannerAttachmentService {

    Optional<PlannerAttachment> getByPlannerIdAndDriverId(String ownerId, String driverId);

}
