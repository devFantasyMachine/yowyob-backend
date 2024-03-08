package cm.yowyob.auth.app.infrastructure.entities.users;


import cm.yowyob.auth.app.domain.model.user.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@IdClass(UserEntity.UserEntityId.class)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "tenant_id", "username" }),
        @UniqueConstraint(columnNames = { "tenant_id", "phone" }),
        @UniqueConstraint(columnNames = { "tenant_id", "email" })})
public class UserEntity implements Serializable {

    @Id
    @NonNull
    @Column(name = "user_id", updatable = false, unique = true, nullable = false)
    private String userId;

    @Id
    @NonNull
    @Column(name = "tenant_id", nullable = false, updatable = false)
    private UUID tenantId;


    @Column(updatable = false)
    private String creator;

    @Column(updatable = false, nullable = false)
    private String username;//unique

    @Embedded
    private ProfileEntity profile;

    private String password;

    @Column(nullable = false)
    private Set<String> roles;

    private String phone;
    private Boolean phoneVerified;

    private String email;
    private Boolean emailVerified;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<RequireAction> requireActions;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    private LocalDateTime lastLoginInstant;
    private LocalDateTime expiry;

    private String parentEmail;

    @Embedded
    private UserTwoFactorConfigurationEntity twoFactorConfiguration;

    @Embedded
    private UserRecoveryConfigurationEntity userRecoveryConfiguration;

    @OneToMany(fetch = FetchType.EAGER)
    private List<UserRegistrationEntity> registrations = new ArrayList<>();


    @Column(nullable = false)
    private UserState.State userState;



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserEntityId {

        private String userId;
        private UUID tenantId;
    }



}

