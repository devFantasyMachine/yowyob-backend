package cm.yowyob.letsgo.driver.domain.handler;


import cm.yowyob.letsgo.driver.domain.events.DriverEventPublisher;
import cm.yowyob.letsgo.driver.domain.exception.*;
import cm.yowyob.letsgo.driver.domain.model.*;
import cm.yowyob.letsgo.driver.domain.model.driver.*;
import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.domain.model.identities.IdentityProvider;
import cm.yowyob.letsgo.driver.domain.model.identities.IdentityType;
import cm.yowyob.letsgo.driver.domain.model.resources.Score;
import cm.yowyob.letsgo.driver.domain.ports.*;

import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;


@AllArgsConstructor
public class DriverHandler {


    private UserService userService;
    private DriverService driverService;
    private DriverEventPublisher eventPublisher;
    private HumanIdentityEventPublisher humanIdentityEventPublisher;
    private IdentityProviderService identityProviderService;
    private NotificationEventProducer notificationEventProducer;


    public Driver getDriver(String DriverId) throws DriverNotFoundException {

        Optional<Driver> optionalDriver = driverService.getById(DriverId);

        if (optionalDriver.isEmpty())
            throw new DriverNotFoundException();

        return optionalDriver.get();
    }


    public Driver upgradeToDriver(String userId)
            throws UserNotFoundException {

        Optional<Driver> optionalDriver = driverService.getById(userId);

        Notification notification = Notification.builder()
                .content("Welcome")
                .subject("driver creation")
                .userId(userId)
                .createAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .type(NotificationType.TENANT)
                .severity(NotificationSeverity.INFO)
                .tenantId("letsgo")
                .build();

        try {
            notificationEventProducer.publish(notification);
        } catch (Exception ignored) {}

        if (optionalDriver.isPresent())
            return optionalDriver.get();

        Profile profile = userService.getProfile(userId);

        Driver newDriver = Driver.builder()
                .driverId(userId)
                .profile(profile)
                .score(Score.INITIAL)
                .isEntreprise(false)
                .yearsOfExperience(0)
                .isIndependent(true)
                .driverAvailability(DriverAvailability.NONE)
                .build();

        Driver saved = driverService.save(newDriver);
        eventPublisher.publishNewDriverEvent(saved);

        return saved;
    }



    public Driver updateDriverBusinessInformation(BusinessInformation businessInformation, String userId)
            throws DriverNotFoundException {

        Driver driver = getDriver(userId);

        driver.setYearsOfExperience(ObjectUtils.getOrDefault(businessInformation.getYearsOfExperience(), driver.getYearsOfExperience()));
        driver.setCvLink(ObjectUtils.getOrDefault(businessInformation.getCvLink(), driver.getCvLink()));
        driver.setDocs(ObjectUtils.getOrDefault(businessInformation.getDocs(), driver.getDocs()));
        driver.setIsIndependent(ObjectUtils.getOrDefault(businessInformation.getIsIndependent(), driver.getIsIndependent()));
        driver.setIsEntreprise(ObjectUtils.getOrDefault(businessInformation.getIsEntreprise(), driver.getIsEntreprise()));
        driver.setBusinessLogo(ObjectUtils.getOrDefault(businessInformation.getBusinessLogo(), driver.getBusinessLogo()));
        driver.setBusinessName(ObjectUtils.getOrDefault(businessInformation.getBusinessName(), driver.getBusinessName()));
        driver.setBusinessCode(ObjectUtils.getOrDefault(businessInformation.getBusinessCode(), driver.getBusinessCode()));
        driver.setBusinessType(ObjectUtils.getOrDefault(businessInformation.getBusinessType(), driver.getBusinessType()));
        driver.setBusinessDescription(ObjectUtils.getOrDefault(businessInformation.getBusinessDescription(), driver.getBusinessDescription()));
        driver.setBusinessWebSite(ObjectUtils.getOrDefault(businessInformation.getBusinessWebSite(), driver.getBusinessWebSite()));
        driver.setBusinessAddress(ObjectUtils.getOrDefault(businessInformation.getBusinessAddresses(), driver.getBusinessAddress()));
        driver.setBusinessTaxationNumber(ObjectUtils.getOrDefault(businessInformation.getBusinessTaxationNumber(), driver.getBusinessTaxationNumber()));
        driver.setBusinessRegistrationNumber(ObjectUtils.getOrDefault(businessInformation.getBusinessRegistrationNumber(), driver.getBusinessRegistrationNumber()));


        eventPublisher.publishUpdateDriverEvent(driver);

        return driverService.save(driver);

    }


    public Driver updateDriverProfile(Profile profile, String userId)
            throws DriverNotFoundException {

        Driver driver = getDriver(userId);


        driver.setVersion(driver.getVersion() + 1);
        driver.setAvatar(ObjectUtils.getOrDefault(profile.getAvatar(), driver.getAvatar()));
        driver.setAbout(ObjectUtils.getOrDefault(profile.getAbout(), driver.getAbout()));
        driver.setAddress(ObjectUtils.getOrDefault(profile.getAddress(), driver.getAddress()));
        driver.setGender(ObjectUtils.getOrDefault(profile.getGender(), driver.getGender()));
        driver.setBirthdate(ObjectUtils.getOrDefault(profile.getBirthdate(), driver.getBirthdate()));
        driver.setKeywords(ObjectUtils.getOrDefault(profile.getKeywords(), driver.getKeywords()));
        driver.setPicture(ObjectUtils.getOrDefault(profile.getPicture(), driver.getPicture()));
        driver.setLastName(ObjectUtils.getOrDefault(profile.getLastName(), driver.getLastName()));
        driver.setFirstName(ObjectUtils.getOrDefault(profile.getFirstName(), driver.getFirstName()));
        driver.setContacts(ObjectUtils.getOrDefault(profile.getContacts(), driver.getContacts()));


        return driverService.save(driver);
    }


    public void updateDriverScore(Score newScore, String DriverId) throws DriverNotFoundException {

        Driver driver = getDriver(DriverId);

        driver.setScore(newScore);
        driverService.save(driver);
    }



    public DriverPricing updatePricing(DriverPricing request, String driverId) throws DriverNotFoundException {

        Driver driver = getDriver(driverId);

        DriverPricing pricing = driver.getDriverPricing();

        pricing.setPricePerDay(ObjectUtils.getOrDefault(request.getPricePerDay(), pricing.getPricePerDay()));
        pricing.setCurrency(ObjectUtils.getOrDefault(request.getCurrency(), pricing.getCurrency()));
        pricing.setPricePerHour(ObjectUtils.getOrDefault(request.getPricePerHour(), pricing.getPricePerHour()));
        pricing.setPricePerKilometer(ObjectUtils.getOrDefault(request.getPricePerKilometer(), pricing.getPricePerKilometer()));
        pricing.setCanNegotiated(ObjectUtils.getOrDefault(request.getCanNegotiated(), pricing.getCanNegotiated()));

        driver.setDriverPricing(pricing);
        Driver saved = driverService.save(driver);
        return saved.getDriverPricing();
    }


    public DriverAvailability updateAvailability(DriverAvailability availability, String driverId)
            throws DriverNotFoundException {

        Driver driver = getDriver(driverId);

        DriverAvailability oldDriverAvailability = driver.getDriverAvailability();

        if (oldDriverAvailability == null){

            driver.setDriverAvailability(availability);
        }
        else {

            Map<DayOfWeek, TreeSet<TimeRange>> oldTimeRangePerDay =
                    oldDriverAvailability.getTimeRangePerDay();


            if (availability.getIsAvailable() != null)
                oldDriverAvailability.setIsAvailable(availability.getIsAvailable());


            if (availability.getTimeRangePerDay() != null &&
                    !availability.getTimeRangePerDay().isEmpty()){

                for (DayOfWeek dayOfWeek : availability.getTimeRangePerDay().keySet()) {

                    oldTimeRangePerDay.put(dayOfWeek, availability.getTimeRangePerDay().get(dayOfWeek));
                }
            }


            DriverAvailability newDriverAvailability = DriverAvailability.builder()
                    .isAvailable(oldDriverAvailability.getIsAvailable())
                    .timeRangePerDay(oldTimeRangePerDay)
                    .build();

            newDriverAvailability.setTimeRangePerDay(oldTimeRangePerDay);
            driver.setDriverAvailability(newDriverAvailability);

        }






        Driver savedDriver = driverService.save(driver);


        return savedDriver.getDriverAvailability();
    }


    public Set<DriverSkill> addOrUpdateSkill(DriverSkill skill, String driverId) throws DriverNotFoundException {

        Driver driver = getDriver(driverId);

        Set<DriverSkill> skills = ObjectUtils.getMutableSet(driver.getSkills());

        skills.removeIf(skill1 -> skill1.name().equals(skill.name()));

        skills.add(skill);
        driver.setSkills(skills);

        Driver saved = driverService.save(driver);
        return saved.getSkills();
    }



    public Set<DriverSkill> removeSkill(String skill, String driverId) throws DriverNotFoundException {

        Driver driver = getDriver(driverId);

        Set<DriverSkill> skills = ObjectUtils.getMutableSet(driver.getSkills());

        skills.removeIf(skill1 -> skill1.name().equals(skill));
        driver.setSkills(skills);

        driverService.save(driver);
        return driver.getSkills();
    }






    public List<HumanIdentity> addDriverLicence(HumanIdentity request, String driverId)
            throws DriverNotFoundException,
            InvalidHumanIdentityTypeForDriverLicenceException {

        if (request.getType() != IdentityType.PERMIT && request.getType() != IdentityType.CNI)
            throw new InvalidHumanIdentityTypeForDriverLicenceException();

        HumanIdentity humanIdentity = HumanIdentity.builder()
                .identityUId(request.getIdentityUId())
                .type(request.getType())
                .docs(request.getDocs())
                .verifiedAt(null)
                .formattedIdentityProvider(request.getFormattedIdentityProvider())
                .providerId(null)
                .createdAt(LocalDateTime.now())
                .expireAt(request.getExpireAt())
                .issueAt(request.getIssueAt())
                .isVerified(false)
                .build();

        Driver driver = getDriver(driverId);
        List<HumanIdentity> licences = ObjectUtils.getMutableList(driver.getDriverLicences());

        licences.removeIf(driverLicence1 -> Objects.equals(driverLicence1.getIdentityUId(), humanIdentity.getIdentityUId()));

        licences.add(humanIdentity);
        driver.setDriverLicences(licences);

        driverService.save(driver);
        humanIdentityEventPublisher.publishForEvaluation(humanIdentity, driver.getDriverId());

        return driver.getDriverLicences();
    }


    private HumanIdentity createTrustedHumanIdentity(HumanIdentity request)
            throws IdentityProviderNotFoundException, NonSupportedIdentityTypeException {

        IdentityProvider identityProvider = identityProviderService.getById(request.getProviderId());

        if (identityProvider == null)
            throw new IdentityProviderNotFoundException();

        if (!identityProvider.support(request.getType()))
            throw new NonSupportedIdentityTypeException();

        return HumanIdentity.builder()
                .identityUId(request.getIdentityUId())
                .type(request.getType())
                .docs(request.getDocs())
                .verifiedAt(request.getVerifiedAt())
                .formattedIdentityProvider(identityProvider.getFormatted())
                .providerId(request.getProviderId())
                .createdAt(LocalDateTime.now())
                .expireAt(request.getExpireAt())
                .issueAt(request.getIssueAt())
                .isVerified(request.getIsVerified())
                .build();
    }


    public List<HumanIdentity> removeHumanIdentity(String licenceId, String driverId) throws DriverNotFoundException {

        Driver driver = getDriver(driverId);
        List<HumanIdentity> licences = ObjectUtils.getMutableList(driver.getDriverLicences());

        licences.removeIf(driverLicence1 -> driverLicence1.getIdentityUId()
                .equals(licenceId));

        driver.setDriverLicences(licences);
        Driver savedDriver = driverService.save(driver);

        return savedDriver.getDriverLicences();
    }



    public List<DriverExperience> addDriverExperience(DriverExperience experience, String driverId)
            throws DriverNotFoundException {

        Driver driver = getDriver(driverId);

        List<DriverExperience> driverExperiences =
                ObjectUtils.getMutableList(driver.getExperiences());

        driverExperiences.removeIf(driverExperience -> Objects.equals(driverExperience.getLabel(), experience.getLabel()));

        driverExperiences.add(experience);
        driver.setExperiences(driverExperiences);

        Driver saved = driverService.save(driver);
        return saved.getExperiences();
    }

    public List<DriverExperience> removeDriverExperience(String label, String driverId) throws DriverNotFoundException {

        Driver driver = getDriver(driverId);

        List<DriverExperience> driverExperiences =
                ObjectUtils.getMutableList(driver.getExperiences());

        driverExperiences.removeIf(driverExperience -> driverExperience.getLabel().equals(label));

        driver.setExperiences(driverExperiences);

        driverService.save(driver);
        return driver.getExperiences();

    }


    public WorkZone updateWorkZone(WorkZone workZone, String driverId) throws DriverNotFoundException {

        Driver driver = getDriver(driverId);
        driver.setWorkZone(workZone);
        Driver saved = driverService.save(driver);
        return saved.getWorkZone();
    }


}
