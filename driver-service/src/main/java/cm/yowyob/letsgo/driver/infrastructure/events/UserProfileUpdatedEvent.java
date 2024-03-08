package cm.yowyob.letsgo.driver.infrastructure.events;


import cm.yowyob.letsgo.driver.domain.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdatedEvent {

    private Profile profile;
    private String userId;

}
