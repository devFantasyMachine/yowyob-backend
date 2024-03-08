/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.application;


import cm.yowyob.auth.app.domain.auth.SessionTokenContext;
import cm.yowyob.auth.app.domain.auth.SessionTokenGenerator;
import cm.yowyob.auth.app.domain.model.device.Device;
import cm.yowyob.auth.app.domain.model.user.User;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.UUID;

public class SessionTokenGeneratorImpl implements SessionTokenGenerator {

    private final JwtEncoder jwtEncoder;
    private final String issuer;

    public SessionTokenGeneratorImpl(JwtEncoder jwtEncoder, String issuer) {
        Assert.notNull(jwtEncoder, "jwtEncoder cannot be null");
        Assert.notNull(issuer, "issuer cannot be null");
        this.issuer = issuer;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public String generateSessionToken(final SessionTokenContext context) {

        JwsAlgorithm jwsAlgorithm = SignatureAlgorithm.RS256;

        User user = context.getUser();
        Device device = context.getDevice();

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .subject(user.getUserId().getId())
                .issuer(issuer)
                .audience(Collections.singletonList(device.getDeviceId().toString()))
                .issuedAt(context.getIssuedAt())
                .expiresAt(context.getExpiresAt())
                .notBefore(context.getIssuedAt())
                .id(UUID.randomUUID().toString())
                .claim("deviceId", device.getDeviceId())
                .claim("tenantId", user.getTenantId());

        if (!context.isRememberToken()){

            if (!CollectionUtils.isEmpty(user.getRoles())) {
                claimsBuilder.claim("roles", user.getRoles());
            }

        }

        JwsHeader.Builder jwsHeaderBuilder = JwsHeader.with(jwsAlgorithm);

        JwsHeader jwsHeader = jwsHeaderBuilder.build();
        JwtClaimsSet claims = claimsBuilder.build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims))
                .getTokenValue();

    }




}
