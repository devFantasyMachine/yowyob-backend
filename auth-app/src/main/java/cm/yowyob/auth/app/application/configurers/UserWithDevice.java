package cm.yowyob.auth.app.application.configurers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithDevice implements Principal {

    private String userId;
    private String deviceId;

    @Override
    public String getName() {
        return userId;
    }


}
