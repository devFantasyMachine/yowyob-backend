package cm.yowyob.letsgo.driver.infrastructure.services;

import cm.yowyob.letsgo.driver.domain.ports.UserService;
import cm.yowyob.letsgo.driver.domain.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Service
public class HttpUserService implements UserService {


    final String profileUrl = "https://proxy.yowyob.com/AUTH-SERVICE/api/v0/userinfo/profile/";



    @Autowired
    RestTemplate restTemplate;


    @Override
    public Optional<Profile> getUserProfile(String userId) {

        try {

            ResponseEntity<Profile> responseEntity =
                    restTemplate.getForEntity(profileUrl + userId, Profile.class);

            if (responseEntity.getStatusCode() == HttpStatusCode.valueOf(404)){

                return Optional.empty();
            }

            // server error retry
            return getProfileFromAuthService(userId);

        } catch (RestClientException e) {

            // network error retry
            return getProfileFromAuthService(userId);
        }


    }

    private Optional<Profile> getProfileFromAuthService(String userId) {

        ResponseEntity<Profile> retryResponseEntity;

        try {

            retryResponseEntity = restTemplate.getForEntity(profileUrl + userId, Profile.class);

        } catch (RestClientException e) {
            return Optional.empty();
        }

        if (retryResponseEntity.getStatusCode().isError())
            return Optional.empty();

        return Optional.ofNullable(retryResponseEntity.getBody());
    }


    @Override
    public boolean existById(String userId) {

        return getProfileFromAuthService(userId)
                .isPresent();
    }


}
