package cm.yowyob.letsgo.driver.mappers;

import cm.yowyob.letsgo.driver.application.dto.*;
import cm.yowyob.letsgo.driver.domain.model.driver.Driver;

import java.util.stream.Collectors;

public class DriverIOMapper extends IOMapper<Driver, DriverDTO> {


    @Override
    public Driver toDomainObject(DriverDTO entity) {
        return null;
    }

    @Override
    public DriverDTO toDTO(Driver driver) {
        return driver == null ? null : DriverDTO.builder()
                .driverId(driver.getDriverId())
                .avatar(driver.getAvatar())
                .about(driver.getAbout())
                .address(driver.getAddress())
                .gender(driver.getGender())
                .birthdate(driver.getBirthdate())
                .keywords(driver.getKeywords())
                .version(driver.getVersion())
                .picture(driver.getPicture())
                .lastName(driver.getLastName())
                .firstName(driver.getFirstName())
                .contacts(driver.getContacts())
                .score(driver.getScore())
                .yearsOfExperience(driver.getYearsOfExperience())
                .cvLink(driver.getCvLink())
                .docs(driver.getDocs())
                .isIndependent(driver.getIsIndependent())
                .skills(driver.getSkills() == null ? null : driver.getSkills().stream()
                        .map(DriverSkillDTO::fromDomainObject)
                        .collect(Collectors.toSet()))
                .experiences(driver.getExperiences() == null ? null : driver.getExperiences()
                        .stream()
                        .map(DriverExperienceDTO::fromDomainObject)
                        .collect(Collectors.toList()))
                .certificate(driver.getCertificate())
                .driverAvailability(DriverAvailabilityDTO.fromDomainObject(driver.getDriverAvailability()))
                .driverPricing(DriverPricingDTO.fromDomainObject(driver.getDriverPricing()))
                .workZone(WorkZoneDTO.fromDomainObject(driver.getWorkZone()))
                .isEntreprise(driver.getIsEntreprise())
                .businessLogo(driver.getBusinessLogo())
                .businessName(driver.getBusinessName())
                .businessCode(driver.getBusinessCode())
                .businessType(driver.getBusinessType())
                .businessDescription(driver.getBusinessDescription())
                .businessWebSite(driver.getBusinessWebSite())
                .businessAddress(driver.getBusinessAddress())
                .businessTaxationNumber(driver.getBusinessTaxationNumber())
                .businessRegistrationNumber(driver.getBusinessRegistrationNumber())
                .businessContacts(driver.getBusinessContacts())
                .driverLicences(driver.getDriverLicences())
                .build();
    }
}
