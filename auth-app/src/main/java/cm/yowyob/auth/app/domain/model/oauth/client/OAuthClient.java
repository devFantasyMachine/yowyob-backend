/*
 * Copyright 2020-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cm.yowyob.auth.app.domain.model.oauth.client;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

import cm.yowyob.auth.app.domain.model.oauth.core.AuthorizationGrantType;
import cm.yowyob.auth.app.domain.model.oauth.core.ClientAuthenticationMethod;

import cm.yowyob.auth.app.domain.util.StringUtils;
import lombok.*;



@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthClient implements Serializable {

	@NonNull
	private String id;

	@NonNull
	private UUID tenantId;

	@NonNull
	private UUID applicationId;

	@NonNull
	private String clientId;

	private Instant clientIdIssuedAt;
	private String clientSecret;
	private Instant clientSecretExpiresAt;
	private String clientName;
	private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
	private Set<AuthorizationGrantType> authorizationGrantTypes;
	private Set<String> redirectUris;
	private Set<String> postLogoutRedirectUris;
	private Set<String> scopes;

	@NonNull
	private ClientSettings clientSettings;

	@NonNull
	private TokenSettings tokenSettings;

	protected OAuthClient() {
	}


	public static Builder withId(String id) {
		Objects.requireNonNull(id, "id cannot be empty");
		return new Builder(id);
	}


	public static Builder from(OAuthClient registeredClient) {
		Objects.requireNonNull(registeredClient, "registeredClient cannot be null");
		return new Builder(registeredClient);
	}

	public static class Builder implements Serializable {

		private UUID tenantId;
		private UUID applicationId;

		private String id;
		private String clientId;
		private Instant clientIdIssuedAt;
		private String clientSecret;
		private Instant clientSecretExpiresAt;
		private String clientName;
		private final Set<ClientAuthenticationMethod> clientAuthenticationMethods = new HashSet<>();
		private final Set<AuthorizationGrantType> authorizationGrantTypes = new HashSet<>();
		private final Set<String> redirectUris = new HashSet<>();
		private final Set<String> postLogoutRedirectUris = new HashSet<>();
		private final Set<String> scopes = new HashSet<>();
		private ClientSettings clientSettings;
		private TokenSettings tokenSettings;







		protected Builder(String id) {
			this.id = id;
		}

		protected Builder(OAuthClient registeredClient) {
			this.id = registeredClient.getId();
			this.tenantId = registeredClient.getTenantId();
			this.applicationId = registeredClient.getApplicationId();

			this.clientId = registeredClient.getClientId();
			this.clientIdIssuedAt = registeredClient.getClientIdIssuedAt();
			this.clientSecret = registeredClient.getClientSecret();
			this.clientSecretExpiresAt = registeredClient.getClientSecretExpiresAt();
			this.clientName = registeredClient.getClientName();
			if (!registeredClient.getClientAuthenticationMethods().isEmpty()) {
				this.clientAuthenticationMethods.addAll(registeredClient.getClientAuthenticationMethods());
			}
			if (!registeredClient.getAuthorizationGrantTypes().isEmpty()) {
				this.authorizationGrantTypes.addAll(registeredClient.getAuthorizationGrantTypes());
			}
			if (!registeredClient.getRedirectUris().isEmpty()) {
				this.redirectUris.addAll(registeredClient.getRedirectUris());
			}
			if (!registeredClient.getPostLogoutRedirectUris().isEmpty()) {
				this.postLogoutRedirectUris.addAll(registeredClient.getPostLogoutRedirectUris());
			}
			if (!registeredClient.getScopes().isEmpty()) {
				this.scopes.addAll(registeredClient.getScopes());
			}
			this.clientSettings = registeredClient.getClientSettings();
			this.tokenSettings = registeredClient.getTokenSettings();
		}


		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder clientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public Builder tenantId(UUID tenantId) {
			this.tenantId = tenantId;
			return this;
		}

		public Builder applicationId(UUID applicationId) {
			this.applicationId = applicationId;
			return this;
		}

		public Builder clientIdIssuedAt(Instant clientIdIssuedAt) {
			this.clientIdIssuedAt = clientIdIssuedAt;
			return this;
		}

		public Builder clientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}

		public Builder clientSecretExpiresAt(Instant clientSecretExpiresAt) {
			this.clientSecretExpiresAt = clientSecretExpiresAt;
			return this;
		}

		public Builder clientName(String clientName) {
			this.clientName = clientName;
			return this;
		}

		public Builder clientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
			this.clientAuthenticationMethods.add(clientAuthenticationMethod);
			return this;
		}


		public Builder clientAuthenticationMethods(
				Consumer<Set<ClientAuthenticationMethod>> clientAuthenticationMethodsConsumer) {
			clientAuthenticationMethodsConsumer.accept(this.clientAuthenticationMethods);
			return this;
		}


		public Builder authorizationGrantType(AuthorizationGrantType authorizationGrantType) {
			this.authorizationGrantTypes.add(authorizationGrantType);
			return this;
		}

		public Builder authorizationGrantTypes(Consumer<Set<AuthorizationGrantType>> authorizationGrantTypesConsumer) {
			authorizationGrantTypesConsumer.accept(this.authorizationGrantTypes);
			return this;
		}

		public Builder redirectUri(String redirectUri) {
			this.redirectUris.add(redirectUri);
			return this;
		}

		public Builder redirectUris(Consumer<Set<String>> redirectUrisConsumer) {
			redirectUrisConsumer.accept(this.redirectUris);
			return this;
		}

		public Builder postLogoutRedirectUri(String postLogoutRedirectUri) {
			this.postLogoutRedirectUris.add(postLogoutRedirectUri);
			return this;
		}

		public Builder postLogoutRedirectUris(Consumer<Set<String>> postLogoutRedirectUrisConsumer) {
			postLogoutRedirectUrisConsumer.accept(this.postLogoutRedirectUris);
			return this;
		}


		public Builder scope(String scope) {
			this.scopes.add(scope);
			return this;
		}


		public Builder scopes(Consumer<Set<String>> scopesConsumer) {
			scopesConsumer.accept(this.scopes);
			return this;
		}


		public Builder clientSettings(ClientSettings clientSettings) {
			this.clientSettings = clientSettings;
			return this;
		}

		/**
		 * Sets the {@link TokenSettings token configuration settings}.
		 *
		 * @param tokenSettings the token configuration settings
		 * @return the {@link Builder}
		 */
		public Builder tokenSettings(TokenSettings tokenSettings) {
			this.tokenSettings = tokenSettings;
			return this;
		}


		public OAuthClient build() {

			isTrue(StringUtils.hasText(this.clientId), "clientId cannot be empty");
			isTrue(!this.authorizationGrantTypes.isEmpty(), "authorizationGrantTypes cannot be empty");

			if (this.authorizationGrantTypes.contains(AuthorizationGrantType.AUTHORIZATION_CODE)) {
				isTrue(!this.redirectUris.isEmpty(), "redirectUris cannot be empty");
			}
			if (!StringUtils.hasText(this.clientName)) {
				this.clientName = this.id;
			}
			if (this.clientAuthenticationMethods.isEmpty()) {
				this.clientAuthenticationMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
			}
			if (this.clientSettings == null) {

				this.clientSettings = new ClientSettings();

				if (isPublicClientType()) {
					// @formatter:off
					this.clientSettings.setRequireProofKey(true);
					this.clientSettings.setRequireAuthorizationConsent(true);

					// @formatter:on
				}
			}
			if (this.tokenSettings == null) {
				this.tokenSettings = TokenSettings.builder().build();
			}

			validateScopes();
			validateRedirectUris();
			validatePostLogoutRedirectUris();
			return create();
		}

		private boolean isPublicClientType() {
			return this.authorizationGrantTypes.contains(AuthorizationGrantType.AUTHORIZATION_CODE) &&
					this.clientAuthenticationMethods.size() == 1 &&
					this.clientAuthenticationMethods.contains(ClientAuthenticationMethod.NONE);
		}

		private OAuthClient create() {
			OAuthClient registeredClient = new OAuthClient();

			registeredClient.id = this.id;
			registeredClient.clientId = this.clientId;
			registeredClient.clientIdIssuedAt = this.clientIdIssuedAt;
			registeredClient.clientSecret = this.clientSecret;
			registeredClient.clientSecretExpiresAt = this.clientSecretExpiresAt;
			registeredClient.clientName = this.clientName;
			registeredClient.clientAuthenticationMethods = Set.copyOf(this.clientAuthenticationMethods);
			registeredClient.authorizationGrantTypes = Set.copyOf(this.authorizationGrantTypes);
			registeredClient.redirectUris = Set.copyOf(this.redirectUris);
			registeredClient.postLogoutRedirectUris = Set.copyOf(this.postLogoutRedirectUris);
			registeredClient.scopes = Set.copyOf(this.scopes);
			registeredClient.clientSettings = this.clientSettings;
			registeredClient.tokenSettings = this.tokenSettings;
			registeredClient.applicationId = applicationId;
			registeredClient.tenantId = tenantId;

			return registeredClient;
		}

		private void validateScopes() {
			if (this.scopes.isEmpty()) {
				return;
			}

			for (String scope : this.scopes) {

				isTrue(validateScope(scope), "scope \"" + scope + "\" contains invalid characters");
			}
		}

		private static boolean validateScope(String scope) {
			return scope == null ||
					scope.chars().allMatch(c -> withinTheRangeOf(c, 0x21, 0x21) ||
							withinTheRangeOf(c, 0x23, 0x5B) ||
							withinTheRangeOf(c, 0x5D, 0x7E));
		}

		private static boolean withinTheRangeOf(int c, int min, int max) {
			return c >= min && c <= max;
		}

		private void validateRedirectUris() {
			if (this.redirectUris.isEmpty()) {
				return;
			}

			for (String redirectUri : this.redirectUris) {
				isTrue(validateRedirectUri(redirectUri),
						"redirect_uri \"" + redirectUri + "\" is not a valid redirect URI or contains fragment");
			}
		}

		private void validatePostLogoutRedirectUris() {
			if (this.postLogoutRedirectUris.isEmpty()) {
				return;
			}

			for (String postLogoutRedirectUri : this.postLogoutRedirectUris) {
				isTrue(validateRedirectUri(postLogoutRedirectUri),
						"post_logout_redirect_uri \"" + postLogoutRedirectUri + "\" is not a valid post logout redirect URI or contains fragment");
			}
		}

		private static boolean validateRedirectUri(String redirectUri) {
			try {
				URI validRedirectUri = new URI(redirectUri);
				return validRedirectUri.getFragment() == null;
			} catch (URISyntaxException ex) {
				return false;
			}
		}

	}

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}



}
