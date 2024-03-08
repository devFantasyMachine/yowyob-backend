package cm.yowyob.auth.app.domain.model.tenant;

import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.user.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;



@Data
@Builder
@AllArgsConstructor
public class Organisation {

    @NonNull
    private String shortName;
    @NonNull
    private String name;
    private String icon;
    private String description;
    private Contact support;
    private Address location;
    private String privacyPolicyUrl;
    private String termOfUseUrl;

}
