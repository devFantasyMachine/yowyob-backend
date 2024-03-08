package cm.yowyob.auth.app.domain.model.roles;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
public abstract class AbstractRole {

    private final UUID tenantId;
    private final String name;
    private String description;
    private ZonedDateTime insertAt;
    private ZonedDateTime updateAt;

}
