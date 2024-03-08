package cm.yowyob.letsgo.driver.domain.events;

import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class HumanIdentityEvent {

    private HumanIdentity humanIdentity;
    private String targetId;
    private String ownerId;

}
