package cm.yowyob.auth.app.domain.model.message.sms;


import cm.yowyob.auth.app.domain.model.contacts.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class SMS {

    private PhoneNumber to;
    private String content;
}
