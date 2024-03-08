package cm.yowyob.auth.app.infrastructure.services;


import cm.yowyob.auth.app.domain.model.ResetPasswordChallenge;
import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.contacts.ContactType;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.model.user.UserState;
import cm.yowyob.auth.app.domain.port.UserService;
import cm.yowyob.auth.app.infrastructure.entities.users.UserEntity;
import cm.yowyob.auth.app.infrastructure.entities.users.UserStateEntity;
import cm.yowyob.auth.app.infrastructure.repositories.*;
import cm.yowyob.auth.app.mappers.ResetPasswordChallengeMapper;
import cm.yowyob.auth.app.mappers.UserMapper;
import cm.yowyob.auth.app.mappers.UserStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    private final UserStateMapper userStateMapper = UserStateMapper.getInstance();

    @Autowired
    UserMapper userMapper;

    @Autowired
    ResetPasswordChallengeMapper resetPasswordChallengeMapper;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    UserStateEntityRepository userStateEntityRepository;

    @Autowired
    ResetPasswordEntityRepository resetPasswordEntityRepository;


    @Override
    public boolean exists(UUID defaultTenantId, Contact contact) {

        if (contact.getType() == ContactType.EMAIL)
            return userEntityRepository.findByTenantIdAndEmail(defaultTenantId, contact.getValue())
                    .isPresent();

        if (contact.getType() == ContactType.PHONE || contact.getType() == ContactType.WHATSAPP)
            return userEntityRepository.findByTenantIdAndPhone(defaultTenantId, contact.getValue())
                    .isPresent();

        return false;
    }

    @Override
    public UserState save(UserState userState) {

        UserStateEntity userStateEntity
                = userStateEntityRepository.save(userStateMapper.toEntity(userState));

        return userStateMapper.toObject(userStateEntity);
    }

    @Override
    public void save(ResetPasswordChallenge resetPasswordChallenge) {

        resetPasswordEntityRepository.save(resetPasswordChallengeMapper.toEntity(resetPasswordChallenge));
    }

    @Override
    public Optional<ResetPasswordChallenge> getResetPasswordChallengeById(String verificationId) {
        return resetPasswordEntityRepository.findById(verificationId)
                .map(resetPasswordChallengeMapper::toObject);
    }


    @Override
    public User save(User user) {

        UserEntity userEntity = userEntityRepository.save(userMapper.toEntity(user));
        return userMapper.toObject(userEntity);
    }


    @Override
    public Optional<User> getByTenantIdAndUserId(UUID tenantId, UserId userId) {
        return userEntityRepository.findByTenantIdAndUserId(tenantId, userId.getId())
                .map(userMapper::toObject);
    }


    @Override
    public Optional<User> getByTenantIdAndEmail(UUID tenantId, String email) {
        return userEntityRepository.findByTenantIdAndEmail(tenantId, email)
                .map(userMapper::toObject);
    }

    @Override
    public Optional<User> getByTenantIdAndPhone(UUID tenantId, String phone) {
        return userEntityRepository.findByTenantIdAndPhone(tenantId, phone)
                .map(userMapper::toObject);
    }

    @Override
    public Optional<User> getByTenantIdAndUsername(UUID tenantId, String username) {
        return userEntityRepository.findByTenantIdAndUsername(tenantId, username)
                .map(userMapper::toObject);
    }


    @Override
    public void delete(User user) {
        userEntityRepository.delete(userMapper.toEntity(user));
    }


}
