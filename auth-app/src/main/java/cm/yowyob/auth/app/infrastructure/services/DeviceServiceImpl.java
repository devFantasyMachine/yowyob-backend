package cm.yowyob.auth.app.infrastructure.services;

import cm.yowyob.auth.app.domain.model.code.FingerPrint;
import cm.yowyob.auth.app.domain.model.device.Device;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.port.DeviceService;
import cm.yowyob.auth.app.infrastructure.entities.DeviceEntity;
import cm.yowyob.auth.app.infrastructure.repositories.DeviceEntityRepository;
import cm.yowyob.auth.app.mappers.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class DeviceServiceImpl implements DeviceService {


    @Autowired
    DeviceEntityRepository deviceEntityRepository;

    @Autowired
    DeviceMapper deviceMapper;


    @Override
    public Device save(Device device) {

        DeviceEntity deviceEntity = deviceEntityRepository.save(deviceMapper.toEntity(device));
        return deviceMapper.toObject(deviceEntity);
    }



    @Override
    public void save(List<Device> devices) {

        deviceEntityRepository.saveAll(deviceMapper.toEntities(devices));
    }

    @Override
    public Optional<Device> getDeviceByDeviceIdAndUserId(UUID deviceId, UserId userId) {

        return deviceEntityRepository.findByDeviceIdAndUserId(deviceId, userId.getId())
                .map(deviceMapper::toObject);

    }

    @Override
    public Set<Device> getAllDevicesByUser(UserId userId) {

        return deviceEntityRepository.findByUserId(userId.getId())
                .stream()
                .map(deviceMapper::toObject)
                .collect(Collectors.toSet());
    }



    @Override
    public Optional<Device> getDeviceByFingerPrintAndUserId(FingerPrint fingerPrint, UserId userId) {
        return deviceEntityRepository.findByFingerPrintAndUserId(fingerPrint.getValue(), userId.getId())
                .map(deviceMapper::toObject);
    }
}
