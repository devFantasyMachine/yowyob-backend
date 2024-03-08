package cm.yowyob.letsgo.driver.domain.model.driver;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DriverAgency {

    private String agencyName;
    private String agencyId;
    private String attachmentId;
}
