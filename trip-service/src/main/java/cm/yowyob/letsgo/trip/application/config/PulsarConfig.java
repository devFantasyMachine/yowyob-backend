package cm.yowyob.letsgo.trip.application.config;


import cm.yowyob.letsgo.trip.domain.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Schema;
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
        };

    }


}
