package cm.yowyob.auth.app.domain.model.tenant;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfiguration {

    public String defaultFromEmail = "change-me@example.com";
    public String defaultFromName;

    public String host = "localhost";
    public Integer port = 25;
    public String username;
    public String password;

    public String properties;
    public EmailSecurityType security = EmailSecurityType.NONE;


    public enum EmailSecurityType {
        NONE,
        SSL,
        TLS;

        EmailSecurityType() {
        }
    }

}
