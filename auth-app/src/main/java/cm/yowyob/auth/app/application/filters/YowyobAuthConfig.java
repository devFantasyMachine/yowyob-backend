/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.application.filters;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.crypto.SecretKey;

@Data
@Builder
@AllArgsConstructor
public class YowyobAuthConfig {


    private static final long ACCESS_EXPIRE_DURATION = 86400000/48; // 24 hours
    private static final long REFRESH_EXPIRE_DURATION = 31104000000L; // 24*30*12 hours

    public static final String AUTHORITIES_CLAIM_NAME = "roles";
    public static final String DEVICE_ID_CLAIM_NAME = "deviceId";
    public static final String REMEMBER_ME_COOKIE_NAME = "remember_me";


    public static String DEFAULT_SECRET_KEY = "917d81a2b99c2e9a7af61e8a6b3cae4f5bd2fc75879862abd819442401284944";


    private String jwtSecretKey;
    private Long expireDuration;
    private SecretKey cookieSecretKey;
    private String prefixPattern;
    private String issuer;
    private final String defaultTargetUrl = "http://localhost:3000";

}
