package cm.yowyob.letsgo.driver.domain.ports;

import cm.yowyob.letsgo.driver.domain.exception.UserNotFoundException;
import cm.yowyob.letsgo.driver.domain.model.Profile;

import java.util.Optional;


public interface UserService {

    Optional<Profile> getUserProfile(String userId);

    boolean existById(String userId);

    default Profile getProfile(String userId) throws UserNotFoundException{

        Optional<Profile> optionalProfile = getUserProfile(userId);

        if (optionalProfile.isEmpty())
            throw new UserNotFoundException();

        return optionalProfile.get();
    }


}
