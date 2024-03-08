package cm.yowyob.auth.app.infrastructure.entities.oauth;

import cm.yowyob.auth.app.domain.model.oauth.SignatureAlgorithm;
import cm.yowyob.auth.app.domain.model.oauth.core.AuthorizationGrantType;
import cm.yowyob.auth.app.domain.model.oauth.core.ClientAuthenticationMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OAuthClientEntity {

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String id;

    @Column(updatable = false, unique = true, nullable = false)
    private String clientId;

    @Column(updatable = false, nullable = false)
    private UUID tenantId;

    @Column(updatable = false, nullable = false)
    private UUID applicationId;

    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> postLogoutRedirectUris;
    private Set<String> scopes;

    @Embedded
    private ClientSettingsEntity clientSettings;

    @Embedded
    private TokenSettingsEntity tokenSettings;

}
