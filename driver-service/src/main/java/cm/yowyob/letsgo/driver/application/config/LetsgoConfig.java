package cm.yowyob.letsgo.driver.application.config;


import cm.yowyob.letsgo.driver.domain.events.DriverEventPublisher;
import cm.yowyob.letsgo.driver.domain.handler.DriverHandler;
import cm.yowyob.letsgo.driver.domain.handler.PlanningHandler;
import cm.yowyob.letsgo.driver.domain.ports.*;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


import java.time.Clock;
import java.time.ZoneId;
import java.util.Collections;


@Configuration
public class LetsgoConfig {


    @Bean
    Clock defaultClock() {

        return Clock.system(ZoneId.of("Africa/Douala"));
    }


    //@LoadBalanced
    @Bean
    RestTemplate restTemplate() {

        var restTemplate = new RestTemplate();

        //restTemplate.getInterceptors().add(new AuthenticationInterceptor());
        restTemplate.setInterceptors(Collections.singletonList(new AuthenticationInterceptor()));

        return restTemplate;
    }


    @Bean
    DriverHandler driverHandler(UserService userService,
                                DriverService driverService,
                                DriverEventPublisher driverEventPublisher,
                                HumanIdentityEventPublisher humanIdentityEventPublisher,
                                IdentityProviderService identityProviderService,
                                NotificationEventProducer notificationEventProducer) {

        return new DriverHandler(userService,
                driverService,
                driverEventPublisher,
                humanIdentityEventPublisher,
                identityProviderService,
                notificationEventProducer);
    }


    @Bean
    PlanningHandler planningHandler(
            DriverPlanningRecord driverPlanningRecord,
            PlannerAttachmentService plannerAttachmentService) {

        return new PlanningHandler(driverPlanningRecord, plannerAttachmentService);
    }




}
