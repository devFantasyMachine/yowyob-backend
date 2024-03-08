package cm.yowyob.letsgo.driver.application.dto;


import cm.yowyob.letsgo.driver.domain.model.driver.TimeRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class DayRangeDTO {


    @Min(1) @Max(7)
    Integer day;

    Set<TimeRange> times;

}
