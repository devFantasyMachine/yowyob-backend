package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.infrastructure.entities.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ResetPasswordEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private String resetId;

    @Column(nullable = false, updatable = false)
    private UUID tenantId;

    @ManyToOne(optional = false)
    @JoinColumns(value = {
            @JoinColumn(referencedColumnName = "user_id", updatable = false, nullable = false),
            @JoinColumn(referencedColumnName = "tenant_id",  updatable = false, nullable = false)})
    private UserEntity target;

    @Column(nullable = false, updatable = false)
    private Instant issueAt;

    @Column(nullable = false, updatable = false)
    private Instant expireAt;

    @Column(nullable = false)
    private Boolean used;

    @Column(nullable = false)
    private Boolean accepted;


}
