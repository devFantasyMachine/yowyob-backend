package cm.yowyob.letsgo.driver.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;



@Data
@Builder
@AllArgsConstructor
@UserDefinedType("skill")
public class DriverSkillEntity {

    private String name;
    @Column("description")
    private String desc;
}
