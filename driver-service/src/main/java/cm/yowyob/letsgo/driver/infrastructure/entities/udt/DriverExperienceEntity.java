package cm.yowyob.letsgo.driver.infrastructure.entities.udt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@UserDefinedType("driver_experience")
public class DriverExperienceEntity {

    private String label;

    @Column("description")
    private String desc;
    private Set<String> attachments;
    private LocalDate starAt;
    private LocalDate endAt;

}
