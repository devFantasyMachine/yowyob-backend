package cm.yowyob.auth.app.infrastructure.entities;

import cm.yowyob.auth.app.domain.model.token.APIKey;
import cm.yowyob.auth.app.domain.model.token.Permission;
import cm.yowyob.auth.app.domain.model.user.UserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class APIKeyEntity {

    @Column(nullable = false, updatable = false)
    private UUID tenantId;

    @Id
    private String key;

    private Instant timestamp;

    @Column(nullable = false, updatable = false)
    private String creator;

    @Column(nullable = false)
    private Boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Permission> permissions;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> ipAccessControlList;


    public static APIKeyEntity fromKey(APIKey apiKey){

        if (apiKey == null)
            return null;

        return APIKeyEntity.builder()
                .tenantId(apiKey.getTenantId())
                .key(apiKey.getKey())
                .permissions(apiKey.getPermissions())
                .ipAccessControlList(apiKey.getIpAccessControlList())
                .enabled(apiKey.getEnabled())
                .creator(apiKey.getCreator() == null ? null : apiKey.getCreator().getId())
                .build();
    }


    public static APIKey toKey(APIKeyEntity apiKey){

        if (apiKey == null)
            return null;

        return APIKey.builder()
                .tenantId(apiKey.getTenantId())
                .key(apiKey.getKey())
                .permissions(apiKey.getPermissions())
                .ipAccessControlList(apiKey.getIpAccessControlList())
                .enabled(apiKey.getEnabled())
                .creator(new UserId(apiKey.getCreator()))
                .build();
    }


}
