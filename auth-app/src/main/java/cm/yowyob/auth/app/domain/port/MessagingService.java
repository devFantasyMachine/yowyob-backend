package cm.yowyob.auth.app.domain.port;


import cm.yowyob.auth.app.domain.model.message.email.Email;
import cm.yowyob.auth.app.domain.model.message.sms.SMS;
import cm.yowyob.auth.app.domain.model.tenant.EmailConfiguration;

public interface MessagingService {

    void sendEmail(Email email, EmailConfiguration emailConfiguration);

    void sendSMS(SMS sms);

}
