package com.example.demo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@ConfigurationProperties(prefix = "twilio")
@Data
@Configuration
//@Service
public class TwilioConfig {
    private String accountSid;
    private String authToken;
    private String serviceSid;


}
