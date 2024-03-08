package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.domain.model.tenant.LoginMethod;
import cm.yowyob.auth.app.domain.model.user.RequireAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, updatable = false)
    private UUID tenantId;

    @Column(nullable = false, updatable = false)
    private Instant issueAt;

    @Column(nullable = false, updatable = false)
    private Instant expireAt;

    @Column(nullable = false)
    private Boolean used;

    @Column(nullable = false)
    private Boolean accepted;

    private String userAgent;

    private String ipAddress;

    private String username;
    private String encodedPassword;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthdate;
    private String timezone;
    private String registrationCode;

    @Column(nullable = false, updatable = false)
    private LoginMethod loginMethod;

    @Column(updatable = false)
    private String redirectUri;

    private Instant acceptedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<RequireAction> requireActions;


}
