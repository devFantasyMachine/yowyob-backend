package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.user.Address;
import cm.yowyob.auth.app.infrastructure.entities.users.AddressEntity;
import cm.yowyob.auth.app.infrastructure.entities.users.ContactEntity;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrganisationEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String shortName;

    private String name;
    private String icon;
    private String description;

    @Embedded
    private ContactEntity support;

    @Embedded
    private AddressEntity location;

    private String privacyPolicyUrl;
    private String termOfUseUrl;
}
