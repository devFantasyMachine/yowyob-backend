package cm.yowyob.auth.app.domain.model.token;


import cm.yowyob.auth.app.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class APIKey {

    private UUID tenantId;
    private String key;
    private Instant timestamp;
    private UserId creator;
    private Boolean enabled;
    private Set<Permission> permissions;
    private Set<String> ipAccessControlList;
    private Map<String, String> metadata = new HashMap<>();


    @Override
    public String toString() {
        return key;
    }

}
