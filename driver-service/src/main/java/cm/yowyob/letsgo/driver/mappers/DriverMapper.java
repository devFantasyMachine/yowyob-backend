package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.domain.model.driver.Driver;
import cm.yowyob.letsgo.driver.infrastructure.entities.DriverEntity;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.ScoreEntity;

import java.time.LocalDate;


public class DriverMapper extends Mapper<Driver, DriverEntity>{

    private final ContactMapper contactMapper = new ContactMapper();
    private final AddressMapper addressMapper = new AddressMapper();

    private final CertificateMapper certificateMapper = new CertificateMapper();
    private final DriverSkillMapper driverSkillMapper = new DriverSkillMapper();
    private final DriverPricingMapper driverPricingMapper = new DriverPricingMapper();
    private final HumanIdentityMapper humanIdentityMapper = new HumanIdentityMapper();
    private final DriverExperienceMapper driverExperienceMapper = new DriverExperienceMapper();

    private final WorkZoneMapper workZoneMapper = new WorkZoneMapper();
    private final DriverAvailabilityMapper driverAvailabilityMapper = new DriverAvailabilityMapper();



    @Override
    public Driver toObject(DriverEntity entity) {

        if (entity  == null)
            return null;

        return Driver.builder()
                .driverId(entity.getDriverId())
                .avatar(entity.getAvatar())
                .avatar(entity.getAvatar())
                .about(entity.getAbout())
                .address(addressMapper.toObjectSet(entity.getAddress()))
                .gender(entity.getGender())
                .birthdate(entity.getBirthdate() == null ? null : LocalDate.from(entity.getBirthdate()))
                .keywords(entity.getKeywords())
                .version(entity.getVersion())
                .picture(entity.getPicture())
                .lastName(entity.getLastName())
                .firstName(entity.getFirstName())
                .contacts(contactMapper.toObjectSet(entity.getContacts()))
                .score(ScoreEntity.toScore(entity.getScore()))

                .yearsOfExperience(entity.getYearsOfExperience())
                .cvLink(entity.getCvLink())
                .docs(entity.getDocs())
                .isIndependent(entity.getIsIndependent())
                .driverAvailability(driverAvailabilityMapper.toObject(entity.getAvailability()))
                .skills(driverSkillMapper.toObjectSet(entity.getSkills()))
                .driverLicences(humanIdentityMapper.toObjects(entity.getDriverLicences()))
                .experiences(driverExperienceMapper.toObjects(entity.getExperiences()))
                .certificate(certificateMapper.toObject(entity.getCertificate()))
                .driverPricing(driverPricingMapper.toObject(entity.getDriverPricing()))
                .WorkZone(workZoneMapper.toObject(entity.getWorkZone()))
                .isEntreprise(entity.getIsEntreprise())
                .businessLogo(entity.getBusinessLogo())
                .businessName(entity.getBusinessName())
                .businessCode(entity.getBusinessCode())
                .businessType(entity.getBusinessType())
                .businessDescription(entity.getBusinessDescription())
                .businessWebSite(entity.getBusinessWebSite())
                .businessAddresses(addressMapper.toObjectSet(entity.getBusinessAddress()))
                .businessTaxationNumber(entity.getBusinessTaxationNumber())
                .businessRegistrationNumber(entity.getBusinessRegistrationNumber())
                .businessContacts(contactMapper.toObjectSet(entity.getBusinessContacts()))
                .build();
    }



    @Override
    public DriverEntity toEntity(Driver object) {

        if (object  == null)
            return null;

        return DriverEntity.builder()
                .driverId(object.getDriverId())
                .avatar(object.getAvatar())
                .avatar(object.getAvatar())
                .about(object.getAbout())
                .address(addressMapper.toEntitySet(object.getAddress()))
                .gender(object.getGender())
                .birthdate(object.getBirthdate() == null ? null : object.getBirthdate().atStartOfDay())
                .keywords(object.getKeywords())
                .version(object.getVersion())
                .picture(object.getPicture())
                .lastName(object.getLastName())
                .firstName(object.getFirstName())
                .contacts(contactMapper.toEntitySet(object.getContacts()))
                .score(ScoreEntity.fromScore(object.getScore()))

                .yearsOfExperience(object.getYearsOfExperience())
                .cvLink(object.getCvLink())
                .docs(object.getDocs())
                .isIndependent(object.getIsIndependent())
                .availability(driverAvailabilityMapper.toEntity(object.getDriverAvailability()))
                .skills(driverSkillMapper.toEntitySet(object.getSkills()))
                .driverLicences(humanIdentityMapper.toEntitySet(object.getDriverLicences()))
                .experiences(driverExperienceMapper.toEntitySet(object.getExperiences()))
                .certificate(certificateMapper.toEntity(object.getCertificate()))
                .driverPricing(driverPricingMapper.toEntity(object.getDriverPricing()))
                .workZone(workZoneMapper.toEntity(object.getWorkZone()))
                .isEntreprise(object.getIsEntreprise())
                .businessLogo(object.getBusinessLogo())
                .businessName(object.getBusinessName())
                .businessCode(object.getBusinessCode())
                .businessType(object.getBusinessType())
                .businessDescription(object.getBusinessDescription())
                .businessWebSite(object.getBusinessWebSite())
                .businessAddress(addressMapper.toEntitySet(object.getBusinessAddress()))
                .businessTaxationNumber(object.getBusinessTaxationNumber())
                .businessRegistrationNumber(object.getBusinessRegistrationNumber())
                .businessContacts(contactMapper.toEntitySet(object.getBusinessContacts()))
                .build();
    }

}
