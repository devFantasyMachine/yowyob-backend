package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.domain.model.InvitationReason;
import cm.yowyob.auth.app.domain.model.contacts.ContactType;
import cm.yowyob.auth.app.domain.model.contacts.EmailAddress;
import cm.yowyob.auth.app.domain.model.user.Group;
import cm.yowyob.auth.app.domain.model.user.User;
import cm.yowyob.auth.app.infrastructure.entities.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InvitationEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private String invitationId;

    @Column(nullable = false, updatable = false)
    private UUID tenantId;

    @ManyToOne(optional = false)
    @JoinColumns(value = {
            @JoinColumn(referencedColumnName = "user_id", updatable = false, nullable = false),
            @JoinColumn(referencedColumnName = "tenant_id",  updatable = false, nullable = false)})
    private UserEntity sender;

    @ManyToOne(optional = false)
    @JoinColumns(value = {
            @JoinColumn(referencedColumnName = "user_id", updatable = false, nullable = false),
            @JoinColumn(referencedColumnName = "tenant_id",  updatable = false, nullable = false)})
    private UserEntity target;


    @Column(nullable = false, updatable = false)
    private InvitationReason reason;

    @Column(nullable = false, updatable = false)
    private Instant issueAt;

    @Column(nullable = false, updatable = false)
    private Instant expireAt;

    @Column(nullable = false)
    private Boolean used;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private ContactType contactType;

    @Column(nullable = false)
    private Boolean accepted;

    @OneToOne(cascade = CascadeType.ALL)
    private GroupEntity group;


}
