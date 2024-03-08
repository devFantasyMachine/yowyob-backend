package cm.yowyob.letsgo.driver.domain.model.driver;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
public class Certificate {

    public static Certificate NONE = null;

    private LocalDateTime certifiedAt;
    private String certificateLink;
    private String syndicatId;

}
