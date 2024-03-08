package cm.yowyob.letsgo.trip.infrastructure.entities.udt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Set;



@Data
@Builder
@AllArgsConstructor
@UserDefinedType(value = "pooler_info")
public class PoolerInfoEntity {

    String userId;
    String name;
    Integer version;
    Integer score;
    String picture;
    Set<String> phones;
}
