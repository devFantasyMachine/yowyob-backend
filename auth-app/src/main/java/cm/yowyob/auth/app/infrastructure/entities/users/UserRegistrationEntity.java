package cm.yowyob.auth.app.infrastructure.entities.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.*;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationEntity {

    @Id
    private UUID id;


    @ManyToOne(optional = false)
    @JoinColumns(value = {
            @JoinColumn(referencedColumnName = "user_id", updatable = false, nullable = false),
            @JoinColumn(referencedColumnName = "tenant_id",  updatable = false, nullable = false)})
    private UserEntity user;

    @Column(nullable = false, updatable = false)
    private UUID applicationId;

    private String authenticationToken;

    @Column(nullable = false, updatable = false)
    private ZonedDateTime insertInstant;

    private ZonedDateTime lastLoginInstant;
    private ZonedDateTime lastUpdateInstant;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name="USER_REGISTRATION_VALUE_FIELD", joinColumns=@JoinColumn(name="ID"))
    @MapKeyColumn (name="FIELD_ID")
    @Column(name="VALUE")
    private Map<String, String> data;

    private Set<String> roles;


}

