package cm.yowyob.auth.app.infrastructure.services;

import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.model.user.UserRegistration;
import cm.yowyob.auth.app.domain.port.UserRegistrationService;
import cm.yowyob.auth.app.infrastructure.entities.users.UserRegistrationEntity;
import cm.yowyob.auth.app.infrastructure.repositories.UserRegistrationEntityRepository;
import cm.yowyob.auth.app.mappers.UserRegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {


    @Autowired
    UserRegistrationEntityRepository userRegistrationEntityRepository;

    @Autowired
    UserRegistrationMapper userRegistrationMapper;


    @Override
    public UserRegistration save(UserRegistration userRegistration) {

        UserRegistrationEntity entity = userRegistrationEntityRepository.save(userRegistrationMapper.toEntity(userRegistration));
        return userRegistrationMapper.toObject(entity);
    }


    @Override
    public Optional<UserRegistration> getByApplicationIdAndUserId(UUID appId, UserId userId) {

        return userRegistrationEntityRepository.findByApplicationIdAndUserUserId(appId, userId.getId())
                .map(userRegistrationMapper::toObject);
    }



    @Override
    public List<UserRegistration> getAllByAppId(UUID appId) {

        return userRegistrationMapper.toObjects(userRegistrationEntityRepository.findAllByApplicationId(appId));
    }


}
