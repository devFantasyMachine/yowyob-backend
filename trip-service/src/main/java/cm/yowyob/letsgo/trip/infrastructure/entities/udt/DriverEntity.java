package cm.yowyob.letsgo.trip.infrastructure.entities.udt;



import cm.yowyob.letsgo.trip.infrastructure.entities.udt.ScoreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@UserDefinedType("driver")
public class DriverEntity {

    @Column("driver_id")
    private final String driverId;

    @Column("owner_id")
    private final String ownerId;

    private final Integer version;
    private final String firstName;
    private final String lastName;
    private final String avatar;
    private final String pseudo;
    private final String picture;
    private final String gender;
    private final String about;
    private final ScoreEntity score;
    private final String phone;
    private final Set<String> docs;
    private final String cv;
    private final Integer yearsOfExperience;

}
