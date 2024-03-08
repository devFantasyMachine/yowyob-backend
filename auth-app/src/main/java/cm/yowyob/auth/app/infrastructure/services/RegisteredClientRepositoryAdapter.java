package cm.yowyob.auth.app.infrastructure.services;


import cm.yowyob.auth.app.domain.model.oauth.SignatureAlgorithm;
import cm.yowyob.auth.app.domain.model.oauth.client.OAuthClient;
import cm.yowyob.auth.app.domain.port.OAuthClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Objects;


@Service
public class RegisteredClientRepositoryAdapter implements RegisteredClientRepository {


    private final OAuthClientService delegate;


    @Autowired
    public RegisteredClientRepositoryAdapter(OAuthClientService delegate) {
        Objects.requireNonNull(delegate, "oAuthClientService cannot be null");
        this.delegate = delegate;
    }


    @Override
    public final void save(RegisteredClient registeredClient) {
        delegate.save(convert(registeredClient));
    }


    @Override
    public final RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return convert(delegate.getById(id).orElse(null));
    }


    @Override
    public final RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return convert(delegate.getByClientId(clientId).orElse(null));
    }




    private OAuthClient convert(RegisteredClient registeredClient) {

        if (registeredClient == null)
            return null;

        ClientSettings clientSettings = registeredClient.getClientSettings();

        TokenSettings tokenSettings = registeredClient.getTokenSettings();


        return OAuthClient.withId(registeredClient.getId())
                .clientId(registeredClient.getClientId())
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                .clientSecret(registeredClient.getClientSecret())
                .clientSecretExpiresAt(registeredClient.getClientSecretExpiresAt())
                .clientName(registeredClient.getClientName())
                .tenantId(clientSettings.getSetting("tenantId"))
                .applicationId(clientSettings.getSetting("applicationId"))
                .scopes((scopes) -> scopes.addAll(registeredClient.getScopes()))
                .redirectUris((uris) -> uris.addAll(registeredClient.getRedirectUris()))
                .clientAuthenticationMethods(authenticationMethods ->

                        registeredClient.getClientAuthenticationMethods()
                                .stream()
                                .map(ClientAuthenticationMethod::getValue)
                                .map(cm.yowyob.auth.app.domain.model.oauth.core.ClientAuthenticationMethod::from)
                                .forEach(authenticationMethods::add)
                )
                .authorizationGrantTypes((grantTypes) ->

                        registeredClient.getAuthorizationGrantTypes()
                                .stream()
                                .map(AuthorizationGrantType::getValue)
                                .map(cm.yowyob.auth.app.domain.model.oauth.core.AuthorizationGrantType::from)
                                .forEach(grantTypes::add)
                )
                .clientSettings(cm.yowyob.auth.app.domain.model.oauth.client.ClientSettings.builder()
                        .requireProofKey(clientSettings.isRequireProofKey())
                        .requireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent())
                        .build()
                )
                .tokenSettings(cm.yowyob.auth.app.domain.model.oauth.client.TokenSettings.builder()
                        .isReuseRefreshTokens(tokenSettings.isReuseRefreshTokens())
                        .accessTokenFormat(cm.yowyob.auth.app.domain.model.oauth.core.OAuth2TokenFormat.from(tokenSettings.getAccessTokenFormat().getValue()))
                        .authorizationCodeTimeToLive(tokenSettings.getAuthorizationCodeTimeToLive())
                        .accessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive())
                        .refreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive())
                        .idTokenSignatureAlgorithm(Objects.requireNonNull(SignatureAlgorithm.from(tokenSettings.getIdTokenSignatureAlgorithm().getName())))
                        .build()
                )
                .build();
    }



    private RegisteredClient convert(OAuthClient oAuthClient) {

        if (oAuthClient == null)
            return null;

        cm.yowyob.auth.app.domain.model.oauth.client.ClientSettings authClientClientSettings = oAuthClient.getClientSettings();


        ClientSettings clientSettings = ClientSettings.builder()
                .setting("tenantId", oAuthClient.getTenantId())
                .setting("applicationId", oAuthClient.getApplicationId())
                .requireAuthorizationConsent(authClientClientSettings.isRequireAuthorizationConsent())
                .requireProofKey(authClientClientSettings.isRequireProofKey())
                .build();


        cm.yowyob.auth.app.domain.model.oauth.client.TokenSettings authClientTokenSettings = oAuthClient.getTokenSettings();


        TokenSettings tokenSettings = TokenSettings.builder()
                .accessTokenFormat(new OAuth2TokenFormat(authClientTokenSettings.getAccessTokenFormat().getValue()))
                .accessTokenTimeToLive(authClientTokenSettings.getAccessTokenTimeToLive())
                .authorizationCodeTimeToLive(authClientTokenSettings.getAccessTokenTimeToLive())
                .idTokenSignatureAlgorithm(org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.from(authClientTokenSettings.getIdTokenSignatureAlgorithm().getName()))
                .refreshTokenTimeToLive(authClientTokenSettings.getAccessTokenTimeToLive())
                .reuseRefreshTokens(authClientTokenSettings.isReuseRefreshTokens())
                .build();

        return RegisteredClient.withId(oAuthClient.getId())
                .clientId(oAuthClient.getClientId())
                .clientIdIssuedAt(oAuthClient.getClientIdIssuedAt())
                .clientSecret(oAuthClient.getClientSecret())
                .clientSecretExpiresAt(oAuthClient.getClientSecretExpiresAt())
                .clientName(oAuthClient.getClientName())
                .clientAuthenticationMethods(authenticationMethods -> {

                            oAuthClient.getClientAuthenticationMethods()
                                    .stream()
                                    .map(cm.yowyob.auth.app.domain.model.oauth.core.ClientAuthenticationMethod::getValue)
                                    .map(ClientAuthenticationMethod::new)
                                    .forEach(authenticationMethods::add);
                        }
                )
                .authorizationGrantTypes((grantTypes) -> {

                            oAuthClient.getAuthorizationGrantTypes()
                                    .stream()
                                    .map(cm.yowyob.auth.app.domain.model.oauth.core.AuthorizationGrantType::getValue)
                                    .map(AuthorizationGrantType::new)
                                    .forEach(grantTypes::add);
                        }
                )
                .redirectUris((uris) -> uris.addAll(oAuthClient.getRedirectUris()))
                .scopes((scopes) -> scopes.addAll(oAuthClient.getScopes()))
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();

    }


}
