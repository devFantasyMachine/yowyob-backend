package cm.yowyob.letsgo.driver.infrastructure.entities.udt;

import cm.yowyob.letsgo.driver.domain.model.ContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("urgency_contact")
public class UrgencyContactEntity {

        private String value;
        @Column("is_verified")
        private boolean isVerified;
        @Column("is_favorite")
        private boolean isFavorite;
        @Column("verified_at")
        private LocalDateTime isVerifiedAt;
        @Column("created_at")
        private LocalDateTime createdAt;
        @Column("contact_type")
        private ContactType type;

        private String role;
}
