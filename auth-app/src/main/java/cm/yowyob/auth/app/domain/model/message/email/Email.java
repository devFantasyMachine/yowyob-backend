package cm.yowyob.auth.app.domain.model.message.email;



import cm.yowyob.auth.app.domain.model.contacts.EmailAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;



@Builder
@AllArgsConstructor
public class Email {

    public List<Attachment> attachments;
    public List<EmailAddress> bcc;
    public List<EmailAddress> cc;
    public EmailAddress from;
    public String html;
    public EmailAddress replyTo;
    public String subject;
    public String text;
    public List<EmailAddress> to;
}
