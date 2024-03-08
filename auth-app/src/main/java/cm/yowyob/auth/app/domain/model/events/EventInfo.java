package cm.yowyob.auth.app.domain.model.events;

import cm.yowyob.auth.app.domain.model.user.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInfo {

    private Map<String, Object> data;
    private String deviceDescription;
    private String deviceName;
    private String deviceType;
    private String ipAddress;
    private Address location;
    private String os;
    private String userAgent;

}
