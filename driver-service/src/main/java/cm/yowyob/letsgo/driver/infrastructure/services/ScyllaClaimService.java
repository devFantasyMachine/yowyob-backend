package cm.yowyob.letsgo.driver.infrastructure.services;

import cm.yowyob.letsgo.driver.domain.ports.ClaimService;
import cm.yowyob.letsgo.driver.domain.model.resources.Claim;
import cm.yowyob.letsgo.driver.domain.model.resources.UserResourceType;
import cm.yowyob.letsgo.driver.infrastructure.entities.ClaimEntity;
import cm.yowyob.letsgo.driver.infrastructure.repositories.ClaimRepository;
import cm.yowyob.letsgo.driver.mappers.ClaimMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ScyllaClaimService implements ClaimService  {

    private static final ClaimMapper claimMapper = new ClaimMapper();


    @Autowired
    ClaimRepository claimRepository;


    @Override
    public Set<Claim> getAllActiveByUser(String userId, UserResourceType userResourceType) {

        Set<ClaimEntity> userClaimEntities =
                claimRepository.findAllByUserId(userId);

        return  userClaimEntities
                .stream()
                .filter(ClaimEntity::getIsActive)
                .filter(claimEntity -> claimEntity.getUserResourceType() == userResourceType)
                .map(claimMapper::toObject)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Claim> getAllByUser(String userId, UserResourceType userResourceType) {

        Set<ClaimEntity> userClaimEntities =
                claimRepository.findAllByUserId(userId);

        return  userClaimEntities
                .stream()
                .filter(claimEntity -> claimEntity.getUserResourceType() == userResourceType)
                .map(claimMapper::toObject)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Claim> getAllActiveByResourceId(String resourceId) {

        Set<ClaimEntity> userClaimEntities =
                claimRepository.findAllByResourceId(resourceId);

        return  userClaimEntities
                .stream()
                .filter(ClaimEntity::getIsActive)
                .map(claimMapper::toObject)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Claim> getAllActiveByResourceIds(List<String> resourceIds) {

        Set<ClaimEntity> userClaimEntities =
                claimRepository.findAllByResourceIdIn(resourceIds);

        return  userClaimEntities
                .stream()
                .filter(ClaimEntity::getIsActive)
                .map(claimMapper::toObject)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Claim> saveAll(List<Claim> claims) {

        List<ClaimEntity> claimEntities = claimMapper.toEntities(claims);
        List<ClaimEntity> savedClaimEntities = claimRepository.saveAll(claimEntities);

        return claimMapper.toObjects(savedClaimEntities);
    }

}
