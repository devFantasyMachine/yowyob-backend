package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.yowyob.letsgo.trip.domain.model.DraftTrip;
import cm.yowyob.letsgo.trip.domain.ports.DraftTripService;
import cm.yowyob.letsgo.trip.infrastructure.entities.DraftTripEntity;
import cm.yowyob.letsgo.trip.infrastructure.repositories.DraftTripEntityRepository;
import cm.yowyob.letsgo.trip.mappers.DraftTripMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
public class ScyllaDraftService implements DraftTripService  {

    private final DraftTripMapper draftTripMapper = new DraftTripMapper();


    @Autowired
    private DraftTripEntityRepository draftTripEntityRepository;


    //@Cacheable(value = "#draftId")
    @Override
    public Optional<DraftTrip> getDraftById(UUID draftId) {

        DraftTripEntity entity = draftTripEntityRepository.findByDraftId(draftId);

        if (entity == null)
            return Optional.empty();

        return Optional.ofNullable(draftTripMapper.toObject(entity));
    }

    @Override
    public DraftTrip save(DraftTrip newDraftTrip) {

        DraftTripEntity entity = draftTripEntityRepository.save(draftTripMapper.toEntity(newDraftTrip));

        return draftTripMapper.toObject(entity);
    }

    @Override
    public Optional<DraftTrip> getDraftByIdAndOwnerId(UUID draftId, String userId) {

        DraftTripEntity entity = draftTripEntityRepository.findByDraftIdAndOwnerId(draftId, userId);

        if (entity == null)
            return Optional.empty();

        return Optional.ofNullable(draftTripMapper.toObject(entity));
    }

    @Override
    public List<DraftTrip> getAllByOwnerId(String userId) {

        List<DraftTripEntity> entities = draftTripEntityRepository.findAllByOwnerId(userId);
        return draftTripMapper.toObjects(entities);
    }



}
