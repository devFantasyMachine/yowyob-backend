package cm.yowyob.letsgo.driver.infrastructure.events;


import cm.yowyob.letsgo.driver.domain.events.HumanIdentityEvent;
import cm.yowyob.letsgo.driver.domain.model.Notification;
import cm.yowyob.letsgo.driver.domain.model.NotificationSeverity;
import cm.yowyob.letsgo.driver.domain.model.NotificationType;
import cm.yowyob.letsgo.driver.domain.model.driver.Driver;
import cm.yowyob.letsgo.driver.infrastructure.search.SearchableDriverEntity;
import cm.yowyob.letsgo.driver.infrastructure.search.SearchableDriverEntityRepository;
import cm.yowyob.letsgo.driver.infrastructure.search.SearchableObjectEvent;
import cm.yowyob.letsgo.driver.mappers.DriverPricingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Slf4j
@Configuration
public class CustomApplicationEventListener {

    private final PulsarTemplate<HumanIdentityEvent> humanIdentityEventPulsarTemplate;

    private final PulsarTemplate<Driver> driverEventPulsarTemplate;

    private final PulsarTemplate<Notification> notificationPulsarTemplate;

    private final SearchableDriverEntityRepository driverEntityRepository;

    public CustomApplicationEventListener(PulsarTemplate<HumanIdentityEvent> humanIdentityEventPulsarTemplate, PulsarTemplate<Driver> driverEventPulsarTemplate, PulsarTemplate<Notification> notificationPulsarTemplate, SearchableDriverEntityRepository driverEntityRepository) {
        this.humanIdentityEventPulsarTemplate = humanIdentityEventPulsarTemplate;
        this.driverEventPulsarTemplate = driverEventPulsarTemplate;
        this.notificationPulsarTemplate = notificationPulsarTemplate;
        this.driverEntityRepository = driverEntityRepository;
    }


    @Async
    @EventListener
    public void LetsgoEventWrapperListener(LetsgoEventWrapper letsgoEventWrapper) throws Exception{

        // pub on pulsar
        System.out.println(letsgoEventWrapper);
        Object letsgoEvent = letsgoEventWrapper.getLetsgoEvent();

        if (letsgoEvent instanceof HumanIdentityEvent humanIdentityEvent){

            newEvaluationEventListener(humanIdentityEvent);

        } else if (letsgoEvent instanceof Driver driver) {

            newDriverEventListener(driver);

        }

    }



    public void newEvaluationEventListener(HumanIdentityEvent humanIdentityEvent) throws Exception{

        // pub on pulsar
        System.out.println("new evaluation");

        if (humanIdentityEventPulsarTemplate != null){

            System.out.println("pub in pulsar");
            humanIdentityEventPulsarTemplate.send("identity-eval-topics", humanIdentityEvent);
        }

    }


    private static final String HIGHER_NOTIFICATION_TOPIC = "higher-incoming-notification-topic";



    public void newDriverEventListener(Driver driver) throws Exception{

        Notification notification = Notification.builder()
                .content("Welcome")
                .subject("driver creation")
                .userId(driver.getDriverId())
                .createAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .type(NotificationType.TENANT)
                .severity(NotificationSeverity.INFO)
                .tenantId("letsgo")
                .build();

        notificationPulsarTemplate.send(HIGHER_NOTIFICATION_TOPIC, notification);

        driverEventPulsarTemplate.send("driver-topics",driver);

        // pub on pulsar
    }



    @Async
    @EventListener
    public void SearchableObjectEventListener(SearchableObjectEvent<?> searchableObjectEvent) throws Exception{

        // TODO add in elastic search
        System.out.println(searchableObjectEvent);

        DriverPricingMapper driverPricingMapper = new DriverPricingMapper();

        if (searchableObjectEvent.getObject() instanceof Driver driver){

            SearchableDriverEntity driverEntity = SearchableDriverEntity.builder()
                    .driverId(driver.getDriverId())
                    .avatar(driver.getAvatar())
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
                    .skills(driver.getSkills())
                    .driverLicences(driver.getDriverLicences())
                    .experiences(driver.getExperiences())
                    .certificate(driver.getCertificate())
                    .driverAvailability(driver.getDriverAvailability())

                    .driverPricing(driverPricingMapper.toEntity(driver.getDriverPricing()))

                    .workZone(driver.getWorkZone())
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
                    .build();

            driverEntityRepository.save(driverEntity);

        }


    }




}
