package cm.yowyob.auth.app.infrastructure.services;

import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.contacts.ContactType;
import cm.yowyob.auth.app.domain.model.message.email.Email;
import cm.yowyob.auth.app.domain.model.message.sms.SMS;
import cm.yowyob.auth.app.domain.model.tenant.EmailConfiguration;
import cm.yowyob.auth.app.domain.port.MessagingService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@ConfigurationProperties(prefix = "yowyob.messaging")
@Data
@Service
public class HttpMessagingService implements MessagingService {

    private String host;
    private String port;



    @Override
    public void sendEmail(Email email, EmailConfiguration emailConfiguration) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String url = getUrl(email.to.get(0));

            APIMessage apiMessage = APIMessage.builder()
                    .to(email.to.get(0).getValue())
                    .content(email.text)
                    .subject(email.subject)
                    .build();

            System.out.println(email.text);

            HttpEntity<APIMessage> requestEntity = new HttpEntity<>(apiMessage, headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(url, requestEntity, Boolean.class);

        }
        catch (Exception e){

            log.error("error when attempt to send message", e);
        }


    }

    @Override
    public void sendSMS(SMS sms) {

    }


    private String getUrl(Contact contact) {

        String url = "http://" + host + ":" + port + "/" + "send-message?method=";

        if (contact.getType() == ContactType.PHONE) {

            url += "SMS";

        } else if (contact.getType() == ContactType.WHATSAPP) {

            url += "WHATSAPP";

        } else {

            url += "EMAIL";
        }
        return url;
    }




}
