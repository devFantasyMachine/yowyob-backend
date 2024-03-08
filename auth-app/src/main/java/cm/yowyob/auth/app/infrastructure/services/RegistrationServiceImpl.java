package cm.yowyob.auth.app.infrastructure.services;

import cm.yowyob.auth.app.domain.model.registration.Registration;
import cm.yowyob.auth.app.domain.port.RegistrationService;
import cm.yowyob.auth.app.infrastructure.entities.RegistrationEntity;
import cm.yowyob.auth.app.infrastructure.repositories.RegistrationEntityRepository;
import cm.yowyob.auth.app.mappers.RegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RegistrationServiceImpl implements RegistrationService {


    @Autowired
    RegistrationMapper registrationMapper;

    @Autowired
    RegistrationEntityRepository registrationEntityRepository;


    @Override
    public Optional<Registration> getById(String registrationId) {

        return registrationEntityRepository.findById(registrationId)
                .map(registrationMapper::toObject);
    }


    @Override
    public Registration save(Registration registration) {

        RegistrationEntity entity = registrationMapper.toEntity(registration);
        RegistrationEntity registrationEntity = registrationEntityRepository.save(entity);

        return registrationMapper.toObject(registrationEntity);
    }




}
