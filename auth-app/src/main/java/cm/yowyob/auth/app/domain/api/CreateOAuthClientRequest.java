package cm.yowyob.auth.app.domain.api;


import cm.yowyob.auth.app.domain.model.oauth.core.AuthorizationGrantType;
import cm.yowyob.auth.app.domain.model.oauth.core.ClientAuthenticationMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOAuthClientRequest {


    private Instant clientSecretExpiresAt;
    private String clientName;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<String> redirectUris;

}
