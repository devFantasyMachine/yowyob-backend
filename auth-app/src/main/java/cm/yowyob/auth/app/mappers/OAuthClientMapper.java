package cm.yowyob.auth.app.mappers;


import cm.yowyob.auth.app.domain.model.oauth.client.OAuthClient;
import cm.yowyob.auth.app.infrastructure.entities.oauth.ClientSettingsEntity;
import cm.yowyob.auth.app.infrastructure.entities.oauth.OAuthClientEntity;
import cm.yowyob.auth.app.infrastructure.entities.oauth.TokenSettingsEntity;
import cm.yowyob.auth.app.mappers.core.Mapper;
import org.springframework.stereotype.Component;


@Component
public final class OAuthClientMapper extends Mapper<OAuthClient, OAuthClientEntity> {


    @Override
    public OAuthClient toObject(OAuthClientEntity entity) {

        return OAuthClient
                .withId(entity.getId())
                .tenantId(entity.getTenantId())
                .clientId(entity.getClientId())
                .applicationId(entity.getApplicationId())
                .clientIdIssuedAt(entity.getClientIdIssuedAt())
                .clientSecret(entity.getClientSecret())
                .clientSecretExpiresAt(entity.getClientSecretExpiresAt())
                .clientName(entity.getClientName())
                .clientAuthenticationMethods(authenticationMethods ->
                        authenticationMethods
                                .addAll(entity.getClientAuthenticationMethods())
                )
                .authorizationGrantTypes((grantTypes) ->
                        grantTypes.addAll(entity.getAuthorizationGrantTypes())
                )
                .redirectUris((uris) -> uris.addAll(entity.getRedirectUris()))
                .postLogoutRedirectUris((uris) -> uris.addAll(entity.getPostLogoutRedirectUris()))
                .scopes((scopes) -> scopes.addAll(entity.getScopes()))
                .clientSettings(ClientSettingsEntity.convert(entity.getClientSettings()))
                .tokenSettings(TokenSettingsEntity.convert(entity.getTokenSettings()))
                .build();
    }




    @Override
    public OAuthClientEntity toEntity(OAuthClient authClient) {

        return OAuthClientEntity.builder()
                .id(authClient.getId())
                .tenantId(authClient.getTenantId())
                .clientId(authClient.getClientId())
                .applicationId(authClient.getApplicationId())
                .clientIdIssuedAt(authClient.getClientIdIssuedAt())
                .clientSecret(authClient.getClientSecret())
                .clientSecretExpiresAt(authClient.getClientSecretExpiresAt())
                .clientName(authClient.getClientName())
                .clientAuthenticationMethods(authClient.getClientAuthenticationMethods())
                .authorizationGrantTypes(authClient.getAuthorizationGrantTypes())
                .redirectUris(authClient.getRedirectUris())
                .postLogoutRedirectUris(authClient.getPostLogoutRedirectUris())
                .scopes(authClient.getScopes())
                .clientSettings(ClientSettingsEntity.convert(authClient.getClientSettings()))
                .tokenSettings(TokenSettingsEntity.convert(authClient.getTokenSettings()))
                .build();

    }



}
