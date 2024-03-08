/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.infrastructure.entities.users;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RoleEntity.RoleId.class)
public class RoleEntity {

    @Id
    private String roleName;

    private String roleDesc;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @Id
    @Column(nullable = false)
    private UUID tenantId;

    @Id
    @Column(nullable = false)
    private UUID appId;

    @ManyToOne()
    private UserEntity creator;


    @Data
    public static class RoleId {

        private String roleName;
        private UUID tenantId;
        private UUID appId;
    }



}
