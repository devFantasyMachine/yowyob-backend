/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.infrastructure.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(ApplicationRoleEntity.RoleId.class)
public class ApplicationRoleEntity {

    @Id
    private String roleName;

    private String roleDesc;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @Column(nullable = false, updatable = false)
    private UUID tenantId;

    @Id
    @Column(nullable = false, updatable = false)
    private UUID appId;

    private Boolean isDefault;

    private Boolean isSuperRole;

    private Boolean isBusiness;



    @Data
    public static class RoleId {

        private String roleName;
        private UUID appId;
    }




}
