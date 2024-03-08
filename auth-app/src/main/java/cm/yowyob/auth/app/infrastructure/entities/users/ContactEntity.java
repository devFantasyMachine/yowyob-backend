package cm.yowyob.auth.app.infrastructure.entities.users;


import cm.yowyob.auth.app.domain.model.contacts.ContactType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ContactEntity {

    @Column(updatable = false, nullable = false)
    private String value;

    private Instant createdAt;

    private Boolean isVerified;
    private LocalDateTime isVerifiedAt;

    @Column(updatable = false, nullable = false)
    private ContactType contactType;

}