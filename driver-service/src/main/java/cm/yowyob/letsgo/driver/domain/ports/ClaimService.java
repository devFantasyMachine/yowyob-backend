package cm.yowyob.letsgo.driver.domain.ports;

import cm.yowyob.letsgo.driver.domain.model.resources.Claim;
import cm.yowyob.letsgo.driver.domain.model.resources.UserResourceType;

import java.util.List;
import java.util.Set;




public interface ClaimService {

    Set<Claim> getAllActiveByUser(String userId, UserResourceType userResourceType);
    Set<Claim> getAllByUser(String userId, UserResourceType userResourceType);
    Set<Claim> getAllActiveByResourceId(String resourceId);
    Set<Claim> getAllActiveByResourceIds(List<String> resourceId);
    List<Claim> saveAll(List<Claim> claims);
}
