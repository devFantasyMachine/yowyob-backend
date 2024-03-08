package cm.yowyob.auth.app.domain.model.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;



@Data
@Builder
@AllArgsConstructor
public class UserGroup {


    private Group group;
    private User user;
    private Instant addAt;
    private Boolean enabled;


}
