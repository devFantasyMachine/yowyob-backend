/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.infrastructure.entities;


import cm.yowyob.auth.app.infrastructure.entities.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ScopeEntity.ScopeId.class)
public class ScopeEntity {

    @Id
    private String name;

    @Id
    private UUID appId;

    private String scopeDesc;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Data
    public static class ScopeId {

        private String name;
        private UUID appId;
    }


}

