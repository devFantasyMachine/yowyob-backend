package cm.yowyob.auth.app.domain.model.application;


import cm.yowyob.auth.app.domain.model.contacts.Contact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
public class GeneralAppInfo {

    @NonNull
    private String shortName;
    @NonNull
    private String name;
    private String icon;
    private String description;
    private Contact support;
    @NonNull
    private String homePage;
    private String privacyPolicyLink;
    private String termOfUseLink;

}

