package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "gmail")
@Configuration
public class GmailConfig {
    private String noReplyAddress;
    private String accountPassword;
}
