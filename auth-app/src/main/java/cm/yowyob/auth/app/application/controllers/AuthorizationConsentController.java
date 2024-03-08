package cm.yowyob.auth.app.application.controllers;

import java.security.Principal;
import java.util.*;


import cm.yowyob.auth.app.domain.exceptions.ApplicationNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.OAuthClientNotFoundException;
import cm.yowyob.auth.app.domain.handlers.ApplicationManager;
import cm.yowyob.auth.app.domain.handlers.UserApplicationRegistrationManager;
import cm.yowyob.auth.app.domain.model.application.Application;
import cm.yowyob.auth.app.domain.model.oauth.client.OAuthClient;
import cm.yowyob.auth.app.domain.model.user.UserId;
import cm.yowyob.auth.app.domain.model.user.UserRegistration;
import cm.yowyob.auth.app.domain.port.OAuthClientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
@AllArgsConstructor
public class AuthorizationConsentController {

    private final OAuthClientService oAuthClientService;
    private final OAuth2AuthorizationConsentService authorizationConsentService;
    @GetMapping(value = "/oauth2/consent")
    public ConsentRequest consentP(Principal principal,
                           HttpServletRequest request,
                           @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                           @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                           @RequestParam(OAuth2ParameterNames.STATE) String state)
            throws OAuthClientNotFoundException {

        // Remove scopes that were already approved
        Set<String> scopesToApprove = new HashSet<>();
        Set<String> previouslyApprovedScopes = new HashSet<>();

        OAuthClient registeredClient = this.oAuthClientService.getByClientId(clientId)
                .orElseThrow(OAuthClientNotFoundException::new);

        OAuth2AuthorizationConsent currentAuthorizationConsent =
                this.authorizationConsentService.findById(registeredClient.getId(), principal.getName());

        Set<String> authorizedScopes;

        if (currentAuthorizationConsent != null) {
            authorizedScopes = currentAuthorizationConsent.getScopes();
        } else {
            authorizedScopes = Collections.emptySet();
        }
        for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
            if (OidcScopes.OPENID.equals(requestedScope)) {
                continue;
            }
            if (authorizedScopes.contains(requestedScope)) {
                previouslyApprovedScopes.add(requestedScope);
            } else {
                scopesToApprove.add(requestedScope);
            }
        }


        return ConsentRequest.builder()
                .clientId(clientId)
                .state(state)
                .scope(withDescription(scopesToApprove))
                .previouslyApprovedScopes(withDescription(previouslyApprovedScopes))
                .userId(principal.getName())
                .build();
    }



    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsentRequest {

        private String clientId;
        private String state;
        private Set<ScopeWithDescription> scope;
        private Set<ScopeWithDescription> previouslyApprovedScopes;
        private String userId;

    }



    private static Set<ScopeWithDescription> withDescription(Set<String> scopes) {
        Set<ScopeWithDescription> scopeWithDescriptions = new HashSet<>();
        for (String scope : scopes) {
            scopeWithDescriptions.add(new ScopeWithDescription(scope));

        }
        return scopeWithDescriptions;
    }

    public static class ScopeWithDescription {
        private static final String DEFAULT_DESCRIPTION = "UNKNOWN SCOPE - We cannot provide information about this permission, use caution when granting this.";
        private static final Map<String, String> scopeDescriptions = new HashMap<>();

        static {
            scopeDescriptions.put(
                    OidcScopes.PROFILE,
                    "This application will be able to read your profile information."
            );
            scopeDescriptions.put(
                    "message.read",
                    "This application will be able to read your message."
            );
            scopeDescriptions.put(
                    "message.write",
                    "This application will be able to add new messages. It will also be able to edit and delete existing messages."
            );
            scopeDescriptions.put(
                    "other.scope",
                    "This is another scope example of a scope description."
            );
        }

        public final String scope;
        public final String description;

        ScopeWithDescription(String scope) {
            this.scope = scope;
            this.description = scopeDescriptions.getOrDefault(scope, DEFAULT_DESCRIPTION);
        }
    }


}