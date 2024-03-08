package cm.yowyob.auth.app.domain.handlers;

import cm.yowyob.auth.app.domain.api.CreateOAuthClientRequest;
import cm.yowyob.auth.app.domain.exceptions.ApplicationNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.TenantNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.UserActionNotAllowedException;
import cm.yowyob.auth.app.domain.model.HexIdGenerator;
import cm.yowyob.auth.app.domain.model.application.Application;
import cm.yowyob.auth.app.domain.model.code.HexCode;
import cm.yowyob.auth.app.domain.model.code.HexCodeGenerator;
import cm.yowyob.auth.app.domain.model.oauth.client.OAuthClient;
import cm.yowyob.auth.app.domain.model.user.Group;
import cm.yowyob.auth.app.domain.model.user.GroupMember;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.port.GroupService;
import cm.yowyob.auth.app.domain.port.OAuthClientService;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
public class OAuthClientManager {



    private OAuthClientService OAuthClientService;
    private TenantManager tenantManager;
    private ApplicationManager appManager;
    private GroupService groupService;



    private final HexCodeGenerator hexCodeGenerator = new HexCodeGenerator();




    public OAuthClient addClientToApp(UUID tenantId,
                                      UUID appId,
                                      UserId userId,
                                      CreateOAuthClientRequest newClient)
            throws TenantNotFoundException, ApplicationNotFoundException, UserActionNotAllowedException {


        Application app = appManager.getById(appId);

        List<GroupMember> members = groupService.getGroupMembers(appId, userId)
                .stream()
                .filter(groupMember -> groupMember.getMemberType() == Group.GroupType.STAFF)
                .toList();

        if (!ApplicationManager.isDeveloper(app, members)) {

            throw new UserActionNotAllowedException();
        }

        HexCode clientIdHexCode = hexCodeGenerator.generate();

        return tenantManager.getTenant(tenantId)
                .filter(tenant -> tenant.isCreator(userId))
                .map(tenant -> OAuthClient.withId(UUID.randomUUID().toString())
                        .tenantId(tenantId)
                        .clientId(clientIdHexCode.getValue())
                        .clientSecret(HexIdGenerator.generate(10))
                        .clientIdIssuedAt(Instant.now())
                        .clientName(newClient.getClientName())
                        .clientSecretExpiresAt(newClient.getClientSecretExpiresAt())
                        .redirectUris(uris -> uris.addAll(newClient.getRedirectUris()))
                        .clientAuthenticationMethods(methods -> methods.addAll(newClient.getClientAuthenticationMethods()))
                        .authorizationGrantTypes(grantTypes -> grantTypes.addAll(newClient.getAuthorizationGrantTypes()))
                        .build())
                .map(OAuthClientService::save)
                .orElseThrow(TenantNotFoundException::new);

    }







}
