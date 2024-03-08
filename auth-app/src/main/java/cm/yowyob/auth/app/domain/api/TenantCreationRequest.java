package cm.yowyob.auth.app.domain.api;


import cm.yowyob.auth.app.domain.model.RequestDetails;
import cm.yowyob.auth.app.domain.model.user.Address;
import lombok.Getter;
import lombok.NonNull;


@Getter
public class TenantCreationRequest extends BaseRequest{

    @NonNull
    private final String shortName;
    @NonNull
    private final String name;
    private final String icon;
    private final String description;
    private final String supportEmail;
    private final Address location;
    private final String privacyPolicyUrl;
    private final String termOfUseUrl;

    @NonNull
    private final String issuer;


    public TenantCreationRequest(RequestDetails details, @NonNull String shortName, @NonNull String name, String icon, String description, String supportEmail, Address location, String privacyPolicyUrl, String termOfUseUrl, @NonNull String issuer) {
        super(details);
        this.shortName = shortName;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.supportEmail = supportEmail;
        this.location = location;
        this.privacyPolicyUrl = privacyPolicyUrl;
        this.termOfUseUrl = termOfUseUrl;
        this.issuer = issuer;
    }
}
