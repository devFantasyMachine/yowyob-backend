package cm.yowyob.auth.app.domain.model.user;


import cm.yowyob.auth.app.domain.model.device.Device;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    private UserId userId;
    private UUID sessionId;
    private LocalDateTime createdAt;
    private LocalDateTime openAt;
    private LocalDateTime closeAt;
    private Boolean isValid;
    private Boolean active;
    private Device device;
    private String ip;
    private String openLocation;
    private String closeLocation;
    private String closeStatus;
}


