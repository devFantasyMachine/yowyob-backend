package cm.yowyob.auth.app.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class AuthWebClient {

    private UUID tenantId;
    private UUID webClientId;
    private String apiKey;
    private Boolean enabled;
}

