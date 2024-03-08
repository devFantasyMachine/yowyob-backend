package cm.yowyob.auth.app.infrastructure.entities.tenant;

import cm.yowyob.auth.app.domain.model.tenant.EmailConfiguration;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmailConfigurationEntity {

    public String emailConfigDefaultFromEmail = "change-me@example.com";
    public String emailConfigDefaultFromName;

    public String emailConfigHost = "localhost";
    public Integer emailConfigPort = 25;
    public String emailConfigUsername;
    public String emailConfigPassword;

    public String emailConfigProperties;
    public EmailConfiguration.EmailSecurityType emailSecurityType = EmailConfiguration.EmailSecurityType.NONE;


    public static EmailConfigurationEntity from(EmailConfiguration emailConfiguration) {

        if (emailConfiguration == null)
            return null;

        return EmailConfigurationEntity.builder()
                .emailConfigDefaultFromEmail(emailConfiguration.getDefaultFromEmail())
                .emailConfigDefaultFromName(emailConfiguration.getDefaultFromName())
                .emailConfigHost(emailConfiguration.getHost())
                .emailConfigPort(emailConfiguration.getPort())
                .emailConfigUsername(emailConfiguration.getUsername())
                .emailConfigPassword(emailConfiguration.getPassword())
                .emailConfigProperties(emailConfiguration.getProperties())
                .emailSecurityType(emailConfiguration.getSecurity())
                .build();
    }




}
