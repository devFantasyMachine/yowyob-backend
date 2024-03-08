package cm.yowyob.auth.app.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeBasedDeletePolicy {

    public static final TimeBasedDeletePolicy DEFAULT = new TimeBasedDeletePolicy(true, 120);


    private Boolean isEnabled;
    private Integer numberOfDaysToRetain = 120;

}
