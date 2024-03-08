package cm.yowyob.letsgo.driver.infrastructure.events;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HumanIdentityEvaluationResultEvent {

    private String identityUid;
    private String userId;
    private String targetId;
    private Boolean isVerified;

}
