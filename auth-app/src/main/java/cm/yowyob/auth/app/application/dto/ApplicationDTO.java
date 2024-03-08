package cm.yowyob.auth.app.application.dto;


import cm.yowyob.auth.app.domain.model.TimeBasedDeletePolicy;
import cm.yowyob.auth.app.domain.model.application.Application;
import cm.yowyob.auth.app.domain.model.application.ApplicationRole;
import cm.yowyob.auth.app.domain.model.application.LoginConfiguration;
import cm.yowyob.auth.app.domain.model.application.Scope;
import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.tenant.FamilyConfig;
import cm.yowyob.auth.app.domain.model.user.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;



@Data
@Builder
@AllArgsConstructor
public class ApplicationDTO {

    @Builder.Default
    public List<ApplicationRole> roles = new ArrayList<>();


    private UUID id;
    private boolean active;
    private boolean verifyRegistration;
    private TimeBasedDeletePolicy registrationDeletePolicy;
    private LoginConfiguration loginConfiguration;
    private boolean passwordLessEnabled;

    private String shortName;
    @NonNull
    private String name;
    private String icon;
    private String description;

    private Contact support;

    private String homePage;
    private String privacyPolicyLink;
    private String termOfUseLink;
    private Set<String> authorizedDomains;


    @Builder.Default
    public List<Scope> scopes = new ArrayList<>();

    @Builder.Default
    private List<Group> groups = new ArrayList<>();


    @Builder.Default
    private Boolean isCountryBased = false;

    @Builder.Default
    private Set<String> availableCountriesCodes = Set.of();

    @Builder.Default
    private FamilyConfig familyConfig = new FamilyConfig();



    public static ApplicationDTO from(Application application) {

        if (application == null)
            return null;

        return ApplicationDTO.builder()
                .id(application.getId())
                .roles(application.roles)
                .scopes(application.scopes)
                .groups(application.getGroups())
                .name(application.getInfo().getName())
                .familyConfig(application.getFamilyConfig())
                .icon(application.getInfo().getIcon())
                .shortName(application.getInfo().getShortName())
                .description(application.getInfo().getDescription())
                .homePage(application.getInfo().getHomePage())
                .registrationDeletePolicy(application.getRegistrationDeletePolicy())
                .loginConfiguration(application.getLoginConfiguration())
                .privacyPolicyLink(application.getInfo().getPrivacyPolicyLink())
                .build();
    }






}
