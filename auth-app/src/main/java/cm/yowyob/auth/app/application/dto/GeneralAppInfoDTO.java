package cm.yowyob.auth.app.application.dto;


import cm.yowyob.auth.app.domain.model.contacts.Contact;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NonNull;

@Data
public class GeneralAppInfoDTO {

    private String shortName;
    private String name;
    private String icon;
    private String description;

    @Email
    private String support;
    private String homePage;
    private String privacyPolicyLink;
    private String termOfUseLink;
}
