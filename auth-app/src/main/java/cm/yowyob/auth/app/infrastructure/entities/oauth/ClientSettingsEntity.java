package cm.yowyob.auth.app.infrastructure.entities.oauth;


import cm.yowyob.auth.app.domain.model.oauth.SignatureAlgorithm;
import cm.yowyob.auth.app.domain.model.oauth.client.ClientSettings;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ClientSettingsEntity {


    private Boolean requireProofKey;
    private Boolean requireAuthorizationConsent;
    private String jwkSetUrl;
    private SignatureAlgorithm tokenEndpointSigningAlgorithm;


    public static ClientSettings convert(ClientSettingsEntity clientSettings) {

        if (clientSettings == null)
            return null;

        return ClientSettings.builder()
                .jwkSetUrl(clientSettings.jwkSetUrl)
                .requireAuthorizationConsent(clientSettings.requireAuthorizationConsent)
                .requireProofKey(clientSettings.requireProofKey)
                .tokenEndpointSigningAlgorithm(clientSettings.tokenEndpointSigningAlgorithm)
                .build();
    }


    public static ClientSettingsEntity convert(ClientSettings clientSettings) {

        if (clientSettings == null)
            return null;

        return ClientSettingsEntity.builder()
                .jwkSetUrl(clientSettings.getJwkSetUrl())
                .requireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent())
                .requireProofKey(clientSettings.isRequireProofKey())
                .tokenEndpointSigningAlgorithm(clientSettings.getTokenEndpointSigningAlgorithm())
                .build();
    }


}


