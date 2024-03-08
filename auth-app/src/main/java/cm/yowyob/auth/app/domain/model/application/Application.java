package cm.yowyob.auth.app.domain.model.application;


import cm.yowyob.auth.app.domain.model.TimeBasedDeletePolicy;
import cm.yowyob.auth.app.domain.model.tenant.FamilyConfig;
import cm.yowyob.auth.app.domain.model.user.Group;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.domain.model.user.UserId;
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
public class Application {

    public static final UUID YOWYOB_AUTH_APP_ID = UUID.fromString("3c232e58-ed0e-4b18-ad48-f4f92793ae32");


    @Builder.Default
    public List<ApplicationRole> roles = new ArrayList<>();

    @Builder.Default
    public List<Scope> scopes = new ArrayList<>();

    @Builder.Default
    private List<Group> groups = new ArrayList<>();


    @NonNull
    private final UUID id;

    @NonNull
    private final UUID tenantId;

    @NonNull
    private final User creator;

    @NonNull
    private GeneralAppInfo info;

    @NonNull
    private Boolean active;

    private Boolean verifyRegistration;

    @Builder.Default
    private TimeBasedDeletePolicy registrationDeletePolicy = TimeBasedDeletePolicy.DEFAULT;

    @NonNull
    @Builder.Default
    private LoginConfiguration loginConfiguration = new LoginConfiguration();

    // TODO: 10/01/2024 in the future
    private boolean passwordLessEnabled;

    @Builder.Default
    private Boolean isCountryBased = false;

    @Builder.Default
    private Set<String> availableCountriesCodes = Set.of();

    @Builder.Default
    private FamilyConfig familyConfig = new FamilyConfig();



    public Application sortRoles() {
        this.roles.sort(ApplicationRole::compareTo);
        return this;
    }


    public boolean isCreator(UserId userId) {
        return creator.getUserId().equals(userId);
    }




}
