/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.domain.auth;


public interface SessionTokenGenerator {

    String generateSessionToken(SessionTokenContext context);

}
