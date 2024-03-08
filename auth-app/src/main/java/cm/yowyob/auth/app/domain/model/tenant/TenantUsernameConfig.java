package cm.yowyob.auth.app.domain.model.tenant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantUsernameConfig {


    public static TenantUsernameConfig NONE = TenantUsernameConfig.builder()
            .minCharacters(0)
            .maxCharacters(Integer.MAX_VALUE)
            .canStartWithNumber(true)
            .withNumbers(true)
            .build();

    public static TenantUsernameConfig DEFAULT_POLICY = TenantUsernameConfig.builder()
            .minCharacters(4)
            .maxCharacters(20)
            .canStartWithNumber(false)
            .withNumbers(true)
            .build();


    private Integer minCharacters = 3;
    private Integer maxCharacters = 30;

    private Boolean withNumbers = true;
    private Boolean canStartWithNumber;
}
