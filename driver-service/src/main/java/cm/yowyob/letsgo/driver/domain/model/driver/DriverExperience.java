package cm.yowyob.letsgo.driver.domain.model.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
public final class DriverExperience {

    private final String label;
    private String desc;
    private final LocalDate starAt;
    private final LocalDate endAt;
    private Set<String> attachments;
}
