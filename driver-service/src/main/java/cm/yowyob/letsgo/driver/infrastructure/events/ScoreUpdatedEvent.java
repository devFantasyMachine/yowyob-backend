package cm.yowyob.letsgo.driver.infrastructure.events;

import cm.yowyob.letsgo.driver.domain.model.resources.Score;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreUpdatedEvent {

    private String userId;
    private Score score;
    private UserType userType;


    public enum UserType {

        DRIVER,

        PLANNER
    }

}
