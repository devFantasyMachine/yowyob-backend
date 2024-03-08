package cm.yowyob.letsgo.driver.infrastructure.services;

import cm.yowyob.letsgo.driver.domain.model.driver.Driver;
import cm.yowyob.letsgo.driver.domain.ports.DriverService;
import cm.yowyob.letsgo.driver.infrastructure.entities.DriverEntity;
import cm.yowyob.letsgo.driver.infrastructure.repositories.DriverEntityRepository;
import cm.yowyob.letsgo.driver.infrastructure.search.SearchableObjectEvent;
import cm.yowyob.letsgo.driver.mappers.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;




@Service
public class ScyllaDriverService implements DriverService {

    @Autowired
    DriverEntityRepository driverEntityRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;


    DriverMapper driverMapper = new DriverMapper();

    @Override
    public boolean existsById(String userId) {
        return driverEntityRepository.existsByDriverId(userId);
    }


    @Override
    public Driver save(Driver driver) {

        DriverEntity driverEntity = driverEntityRepository.save(driverMapper.toEntity(driver));

        applicationEventPublisher.publishEvent(new SearchableObjectEvent<>(this, driver));

        return driverMapper.toObject(driverEntity);
    }

    @Override
    public Optional<Driver> getById(String userId) {

        DriverEntity driverEntity = driverEntityRepository.findByDriverId(userId);

        return Optional.ofNullable(driverMapper.toObject(driverEntity));
    }



}
