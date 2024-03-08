package cm.yowyob.auth.app.domain;

import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorAuthChallenge {


    private String id;
    private Boolean lastUsed;
    private TwoFactorMethod method;
    private String secret;
    private Contact contact;


    public enum TwoFactorMethod {

        EMAIL,
        SMS,
        SECRET,
    }


    public TwoFactorAuthChallenge secure() {
        this.secret = null;
        return this;
    }

}
