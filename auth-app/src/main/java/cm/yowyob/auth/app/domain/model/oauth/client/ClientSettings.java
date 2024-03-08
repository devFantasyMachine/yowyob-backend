package cm.yowyob.auth.app.domain.model.oauth.client;


import cm.yowyob.auth.app.domain.model.oauth.SignatureAlgorithm;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientSettings {

    @Builder.Default
    private boolean requireProofKey = false;

    @Builder.Default
    private boolean requireAuthorizationConsent = false;

    private String jwkSetUrl;
    private SignatureAlgorithm tokenEndpointSigningAlgorithm;

}
