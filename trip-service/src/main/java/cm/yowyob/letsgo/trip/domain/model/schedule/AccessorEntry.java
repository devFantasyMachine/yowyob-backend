package cm.yowyob.letsgo.trip.domain.model.schedule;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccessorEntry {

    private String id;
    private String group;

}
