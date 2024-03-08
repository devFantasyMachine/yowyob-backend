package cm.yowyob.letsgo.driver.application.config;


import cm.yowyob.letsgo.driver.domain.handler.DriverHandler;
import cm.yowyob.letsgo.driver.domain.model.Notification;
import cm.yowyob.letsgo.driver.infrastructure.events.HumanIdentityEvaluationResultEvent;
import cm.yowyob.letsgo.driver.infrastructure.events.ScoreUpdatedEvent;
import cm.yowyob.letsgo.driver.infrastructure.events.UserProfileUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.annotation.EnablePulsar;
import org.springframework.pulsar.core.DefaultSchemaResolver;
import org.springframework.pulsar.core.SchemaResolver;





@Slf4j
@EnablePulsar
@Configuration
public class PulsarConfig {


    @Bean
    public SchemaResolver.SchemaResolverCustomizer<DefaultSchemaResolver> schemaResolverCustomizer() {

        return (schemaResolver) -> {
            schemaResolver.addCustomSchemaMapping(Notification.class,
                    Schema.JSON(Notification.class));

            schemaResolver.addCustomSchemaMapping(UserProfileUpdatedEvent.class,
                    Schema.JSON(UserProfileUpdatedEvent.class));

            schemaResolver.addCustomSchemaMapping(ScoreUpdatedEvent.class,
                    Schema.JSON(ScoreUpdatedEvent.class));
        };

    }


}
