package cm.yowyob.letsgo.driver.infrastructure.services;

import cm.yowyob.letsgo.driver.domain.model.driver.PlannerAttachment;
import cm.yowyob.letsgo.driver.domain.ports.PlannerAttachmentService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ScyllaPlannerAttachmentService implements PlannerAttachmentService {


    @Override
    public Optional<PlannerAttachment> getByPlannerIdAndDriverId(String ownerId, String driverId) {
        return Optional.empty();
    }


}
