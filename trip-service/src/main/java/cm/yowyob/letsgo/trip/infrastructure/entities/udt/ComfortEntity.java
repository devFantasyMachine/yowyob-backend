package cm.yowyob.letsgo.trip.infrastructure.entities.udt;



import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("comfort")
public class ComfortEntity {

    private final String name;
    private final String description;

    public static ComfortEntity fromComfort(Comfort comfort){
        if (comfort == null)
            return null;

        return new ComfortEntity(comfort.name(), comfort.description());
    }

}
