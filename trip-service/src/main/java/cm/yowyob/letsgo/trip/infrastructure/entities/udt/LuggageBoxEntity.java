package cm.yowyob.letsgo.trip.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("luggage")
public class LuggageBoxEntity {

    @Column("box_label")
    private final String boxLabel;
    @Column("box_number")
    private final Integer boxNumber;
    @Column("box_quantity")
    private final Float boxQuantityKg;

}
