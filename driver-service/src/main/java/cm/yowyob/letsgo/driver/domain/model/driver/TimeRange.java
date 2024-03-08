package cm.yowyob.letsgo.driver.domain.model.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;




@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TimeRange implements Comparable<TimeRange> {

    private LocalTime startTime;
    private LocalTime endTime;

    @Override
    public int compareTo(TimeRange o) {
        return startTime.compareTo(o.endTime);
    }


}
