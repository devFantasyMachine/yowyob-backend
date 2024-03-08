package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.infrastructure.entities.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApplicationEntity {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private UUID tenantId;

    @Column(nullable = false)
    private Boolean active;

    private Boolean verifyRegistration;

    @ManyToOne(optional = false)
    @JoinColumns(value = {
            @JoinColumn(referencedColumnName = "user_id", updatable = false, nullable = false),
            @JoinColumn(referencedColumnName = "tenant_id",  updatable = false, nullable = false)})
    private UserEntity creator;


    private boolean passwordLessEnabled;

    @Builder.Default
    private Boolean isCountryBased = false;

    @Builder.Default
    private Set<String> availableCountriesCodes = Set.of();

    @Embedded
    private FamilyConfigEntity familyConfig;

    @Column(nullable = false)
    private Boolean deleteRegistrationPolicyEnabled;

    private Integer numberOfDaysToRetain;


    @Column(nullable = false)
    private String name;

    private String icon;
    private String description;

    @Column(nullable = false)
    private String shortName;

    @Column(nullable = false)
    private String supportEmail;

    @Column(nullable = false)
    private String homePage;

    private String privacyPolicyLink;
    private String termOfUseLink;

    @OneToMany(fetch = FetchType.EAGER)
    @Column(updatable = false, nullable = false)
    private Set<ApplicationRoleEntity> roles;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<ScopeEntity> scopes;

    @OneToMany(fetch = FetchType.EAGER)
    private List<GroupEntity> groups;



}
