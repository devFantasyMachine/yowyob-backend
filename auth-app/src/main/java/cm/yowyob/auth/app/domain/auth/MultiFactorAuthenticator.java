package cm.yowyob.auth.app.domain.auth;

import cm.yowyob.auth.app.domain.port.MessagingService;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class MultiFactorAuthenticator {

    private MessagingService messagingService;



}
