package cm.yowyob.letsgo.driver.infrastructure.entities.udt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalTime;




@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("time_range")
public final class TimeRangeEntity {

    private LocalTime start;
    private LocalTime end;

}
