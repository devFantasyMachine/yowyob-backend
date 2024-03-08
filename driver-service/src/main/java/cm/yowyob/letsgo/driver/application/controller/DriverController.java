package cm.yowyob.letsgo.driver.application.controller;


import cm.yowyob.letsgo.driver.application.dto.*;
import cm.yowyob.letsgo.driver.domain.exception.*;
import cm.yowyob.letsgo.driver.domain.handler.DriverHandler;
import cm.yowyob.letsgo.driver.domain.model.BusinessInformation;
import cm.yowyob.letsgo.driver.domain.model.Profile;
import cm.yowyob.letsgo.driver.domain.model.driver.*;
import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.mappers.DriverIOMapper;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v0/drivers")
public class DriverController {


    @Autowired
    DriverHandler driverHandler;

    private final DriverIOMapper driverIOMapper = new DriverIOMapper();


    @GetMapping(value = "", produces = {"application/json"})
    public DriverDTO getMyPlannerInfo(@NonNull Authentication authentication)
            throws DriverNotFoundException {

        Driver driver = driverHandler.getDriver(authentication.getName());
        return driverIOMapper.toDTO(driver);
    }


    @PutMapping(value = "/profile", produces = {"application/json"})
    public DriverDTO updateDriver(@NonNull Authentication authentication, @RequestBody Profile profile)
            throws DriverNotFoundException {

        Driver driver = driverHandler.updateDriverProfile(profile, authentication.getName());
        return driverIOMapper.toDTO(driver);
    }


    @GetMapping(value = "/{driverId}")
    public DriverDTO getPlannerInfo(@PathVariable String driverId)
            throws DriverNotFoundException {

        Driver driver = driverHandler.getDriver(driverId);
        return driverIOMapper.toDTO(driver);
    }


    @PostMapping("")
    public DriverDTO upgradeToDriver(@NonNull Authentication authentication)
            throws UserNotFoundException {

        Driver driver = driverHandler.upgradeToDriver(authentication.getName());
        return driverIOMapper.toDTO(driver);
    }



    @PutMapping("")
    public DriverDTO updatePlannerBusinessInformation(@NonNull Authentication authentication,
                                                   @RequestBody BusinessInformationDTO request)
            throws DriverNotFoundException {


        BusinessInformation businessInformation = BusinessInformation.builder()
                .yearsOfExperience(request.getYearsOfExperience())
                .cvLink(request.getCvLink())
                .docs(request.getDocs())
                .isIndependent(request.getIsIndependent())
                .isEntreprise(request.getIsEntreprise())
                .businessLogo(request.getBusinessLogo())
                .businessName(request.getBusinessName())
                .businessCode(request.getBusinessCode())
                .businessType(request.getBusinessType())
                .businessDescription(request.getBusinessDescription())
                .businessWebSite(request.getBusinessWebSite())
                .businessAddresses(request.getBusinessAddresses())
                .businessTaxationNumber(request.getBusinessTaxationNumber())
                .businessRegistrationNumber(request.getBusinessRegistrationNumber())
                .businessContacts(request.getBusinessContacts())
                .build();

        Driver driver =
                driverHandler.updateDriverBusinessInformation(businessInformation, authentication.getName());
        return driverIOMapper.toDTO(driver);
    }









    @PutMapping("/pricing")
    public DriverPricingDTO updatePricing(@NonNull Authentication authentication,
                                @RequestBody @Valid DriverPricingDTO pricing)
            throws DriverNotFoundException {

        DriverPricing driverPricing = DriverPricing.builder()
                .pricePerKilometer(pricing.getPricePerKilometer())
                .currency(pricing.getCurrency() == null ? null : Currency.getInstance(pricing.getCurrency().toUpperCase()))
                .pricePerHour(pricing.getPricePerHour())
                .pricePerDay(pricing.getPricePerDay())
                .canNegotiated(pricing.getCanNegotiate())
                .build();

        DriverPricing driverPricing1 = driverHandler.updatePricing(driverPricing, authentication.getName());
        return DriverPricingDTO.fromDomainObject(driverPricing1);
    }


    @PutMapping("/availability")
    public DriverAvailabilityDTO updateAvailability(@NonNull Authentication authentication,
                                                    @RequestBody @Valid @NonNull DriverAvailabilityDTO availability)
            throws DriverNotFoundException {

        DriverAvailability driverAvailability =
                driverHandler.updateAvailability(availability.toDomainObject(), authentication.getName());

        return DriverAvailabilityDTO.fromDomainObject(driverAvailability);
    }



    @PutMapping("/work-zone")
    public WorkZoneDTO updateWorkZone(@NonNull Authentication authentication,
                                @RequestBody @Valid @NonNull WorkZoneDTO workZone)
            throws DriverNotFoundException {

        WorkZone workZone1 = WorkZone.builder()
                .cities(workZone.getCities())
                .country(workZone.getCountry())
                .build();

        WorkZone updatedWorkZone = driverHandler.updateWorkZone(workZone1, authentication.getName());


        return WorkZoneDTO.fromDomainObject(updatedWorkZone);
    }



    @PostMapping("/skills")
    public Set<DriverSkillDTO> addOrUpdateSkill(@NonNull Authentication authentication,
                                   @RequestBody @Valid DriverSkillDTO skillDTO)
            throws DriverNotFoundException {

        DriverSkill skill = new DriverSkill(skillDTO.getName(), skillDTO.getDesc());

        Set<DriverSkill> driverSkills = driverHandler.addOrUpdateSkill(skill, authentication.getName());

        return driverSkills
                .stream()
                .map(DriverSkillDTO::fromDomainObject)
                .collect(Collectors.toSet());
    }


    @DeleteMapping("/skills/{skill}")
    public Set<DriverSkillDTO> removeSkill(@NonNull Authentication authentication,
                            @PathVariable @NonNull String skill)
            throws DriverNotFoundException {

        Set<DriverSkill> driverSkills = driverHandler.removeSkill(skill, authentication.getName());

        return driverSkills
                .stream()
                .map(DriverSkillDTO::fromDomainObject)
                .collect(Collectors.toSet());
    }


    @PostMapping("/experiences")
    public Set<DriverExperienceDTO> addDriverLicence(@NonNull Authentication authentication,
                                   @RequestBody @Valid @NonNull DriverExperienceDTO experience)
            throws DriverNotFoundException {

        DriverExperience driverExperience = DriverExperience.builder()
                .label(experience.getLabel())
                .desc(experience.getDesc())
                .attachments(experience.getAttachments())
                .endAt(experience.getEndAt())
                .starAt(experience.getStartAt())
                .build();

        List<DriverExperience> driverExperiences = driverHandler.addDriverExperience(driverExperience, authentication.getName());
        return driverExperiences
                .stream()
                .map(DriverExperienceDTO::fromDomainObject)
                .collect(Collectors.toSet());
    }


    @DeleteMapping("/experiences/{label}")
    public Set<DriverExperienceDTO> addDriverExperience(@NonNull Authentication authentication,
                                    @PathVariable String label)
            throws DriverNotFoundException {

        List<DriverExperience> driverExperiences = driverHandler.removeDriverExperience(label, authentication.getName());

        return driverExperiences
                .stream()
                .map(DriverExperienceDTO::fromDomainObject)
                .collect(Collectors.toSet());
    }







    @PostMapping("/licences")
    public List<HumanIdentity> addDriverLicence(@NonNull Authentication authentication,
                                          @RequestBody HumanIdentityRequestDTO requestDTO)
            throws DriverNotFoundException, InvalidHumanIdentityTypeForDriverLicenceException {

        HumanIdentity licence = requestDTO.toObject();
        return driverHandler.addDriverLicence(licence, authentication.getName());
    }


    @DeleteMapping("/licences/{licenceId}")
    public List<HumanIdentity> removeHumanIdentity(@NonNull Authentication authentication,
                                 @PathVariable String licenceId)
            throws DriverNotFoundException {

        return driverHandler.removeHumanIdentity(licenceId, authentication.getName());
    }





}
