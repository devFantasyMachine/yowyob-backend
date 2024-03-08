package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "whatsapp")
@Configuration
public class WhatsappConfig {
    @JsonProperty("messaging_product")
    private String messagingProduct;
    private String code;
    private String accountId;
    private String token;
}
