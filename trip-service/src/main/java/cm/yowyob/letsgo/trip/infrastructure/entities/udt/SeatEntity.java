package cm.yowyob.letsgo.trip.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("seat")
public class SeatEntity {

    @Column("place_label")
    private final String placeLabel;

    @Column("place_number")
    private final Integer placeNumber;

    @Column("comforts")
    private final Set<@Frozen ComfortEntity> comfort;


}
