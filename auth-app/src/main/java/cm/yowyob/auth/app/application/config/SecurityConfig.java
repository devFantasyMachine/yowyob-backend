package cm.yowyob.auth.app.application.config;


import cm.yowyob.auth.app.application.SessionTokenGeneratorImpl;
import cm.yowyob.auth.app.domain.auth.SessionTokenGenerator;
import cm.yowyob.auth.app.domain.model.registration.PasswordHelper;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.*;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Configuration
public class SecurityConfig {



    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



    @Bean
    PasswordHelper passwordHelper(PasswordEncoder passwordEncoder){

        return new PasswordHelper() {
            @Override
            public String encode(String rawPassword) {
                return passwordEncoder.encode(rawPassword);
            }

            @Override
            public Boolean matches(String rawPassword, String encodedPassword) {
                return passwordEncoder.matches(rawPassword, encodedPassword);
            }

            @Override
            public String randomPassword() {
                return encode(UUID.randomUUID().toString());
            }
        };
    }



    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        //RSAKey rsaKey = Jwks.generateRsa();

        JWK key = JWK.parse("{\"p\":\"w2fCoCAkbaN7iDKMWblo1-zsc_tU9CJwG4LLgPb_wDI5O9_soptVZ_8ipE5h-WJNj-KAMj4qa04BEUhbGvtybm9VI0jmdz8PadoYUl8tBTxDTmISO_DjKevu_k0E8FI3th_0JMhTiKVGXAcQDszNO0d3WR3jcddW28bhcSQjURE\",\"kty\":\"RSA\",\"q\":\"4wMZK7F6WmElPCgmmTJ9JYXfvGk_-wF-9NKINNw22cWCqJXO45GiiXS3l1IonFk1l5awFHw4oXISThZjWfNx-StOBLflD3LL0hx8a3uhKliX0QdD9hfVqDh_gi6UdJ2fzzpICVGlNW81SeesdmWJzvxaYRi_ExQ7MP4lNbzAFO8\",\"d\":\"DOtok6Jyi1AdLTfeJZA-sHflL0H0EaKhmlSpWGAYFgAVCrq6B5bFFbEzA3asW8BTRmVVe7r-d5L29YO32MTURowqTOyEQvCJUK2-3vXP3lIX3iEOw_gld9CRR22biCGaF--p9jTmZGFeCi8stMmqxW1T3lkFgpRA_Dyq6ElyAiDey4rtkIKBKZaU87mT4ioOB7h3BGFHKisLVdUuam-lHvdfOTub7qBDGXDVFuP4sme0emP4SCPfxRsIqGLjY50vEQs-xEqjHfblGNzIaAT6LJbrAqNkoK1BIXWSW3RL4Fkcnj8INse-OwiaHk3zmyFRYmS6P8FYaBlSmAqX1pFaAQ\",\"e\":\"AQAB\",\"kid\":\"49ce63ca-e270-409c-a54b-f20d04e9bf22\",\"qi\":\"RxIYgh89eCziMn9tq--QuJ3GUO7ZIp1bYcaWEjYSA-m40N4tEtFVMZ4PkS_pLdlq63fI1Gby-t3fe2AXGuyeTzyKnpKkVLESNQczggCWHrXmm5wfI0PMG8TQiToW16NXni_HUhXnpnvMhAOLjnLKlcv_drkcq-lS_3V0WnVMZ0I\",\"dp\":\"Xqv-16W7AIOcbjhPV4lFCph3VxoF5uJofmu4PRtufafXovKyq3Wmw2QRXJm9Th4ahgOvKKiF1MdOLRlpSoFYx7iY_ydqwYuD5D0OW5pRLaAs2-UYAIKNDNqKSBr4sZaj91Ez8D4-eo3YsO2bEhCXvoi5givA7n7uTjzlLPPdSIE\",\"dq\":\"xy4KwbmA3xfpcbTQ2Q3hnDCwNEIsrvGLEQhAFhE51y1jDMQUuLspX22XQCAIHdZwHUdLy02K6qyJvxYJqfzd_X-vfphTVJCmoP35dN0OdXcyDWdQi_Yxst2PkwuHmiJK-6npVrPaPt4KZYPuYBuLJJ9qNVNfI0BXBw-WWmX4KuU\",\"n\":\"rUdfAcBII65L-Q4sy5v3_X60GC4cL3Xb9sWu3swNkEVQiPUEUAKs_vzverk-KDrLNRfCBpA1nVRrEajaGE13dYuHRBZcAsQe6Tv-p2QxVnEm5_2oalD3R7wTXPyias9UqNStqBegwjVwbVQvfA8Qt6ol1Gp913ACNITRJnO210A7RPnyeXDD9Rto2t-bWnBPhZRLuNdB41ZJ4gL1WA6tgof-eiftpfq3SKMqw40-45n60iZbevtarLEDV6MTiNLxJqlZb27efZJMtyP69n-BeevzUUd82eD70cvqJ8m518tKjtEON6Stdejj0lLVeS2dp7kTInQsOckYk5b9yA4C3w\"}");

        JWKSet jwkSet =  new JWKSet(key); // new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }



    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }


    @Bean
    public OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer() {

        return context -> {

            OAuth2TokenClaimsSet.Builder claims = context.getClaims();

            // Customize claims
            Authentication principal = context.getPrincipal();

            Set<String> roles = principal.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            claims.claims((claim) -> {
                claim.put("tenantId", "yowyob");
                claim.put("roles", roles);
            });

        };
    }



    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);

        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);

        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        accessTokenGenerator.setAccessTokenCustomizer(accessTokenCustomizer());

        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();

        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }



    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {

        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:8090")
                .build();
    }



    @Bean
    public SessionTokenGenerator sessionTokenGenerator(
            JWKSource<SecurityContext> jwkSource, AuthorizationServerSettings settings){

        return new SessionTokenGeneratorImpl(new NimbusJwtEncoder(jwkSource), settings.getIssuer());
    }


    @Bean
    OAuth2AuthorizationConsentService authorizationConsentService(JdbcOperations jdbcOperations,
                                                                  RegisteredClientRepository registeredClientRepository){

        return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
    }


    @Bean
    OAuth2AuthorizationService authorizationService(JdbcOperations jdbcOperations,
                                                    RegisteredClientRepository registeredClientRepository){

        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }




    RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder){

        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")
                .redirectUri("https://oidcdebugger.com/debug")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .tokenSettings(TokenSettings.builder()
                        .build()
                )
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build()
                )
                .build();

        RegisteredClient deviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("device-messaging-client")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8000/api/auth/callback")
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(true).requireAuthorizationConsent(true).build())
                .build();


        RegisteredClient registeredClient2 = RegisteredClient.withId("87b43051-1bd5-43a7-90d0-1204e8c646c2")
                .clientId("messaging-client2")
                .clientSecret(passwordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8000/api/auth/callback")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .clientIdIssuedAt(ZonedDateTime.now().toInstant())
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(false).requireAuthorizationConsent(false).build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient, deviceClient, registeredClient2);
    }




}
