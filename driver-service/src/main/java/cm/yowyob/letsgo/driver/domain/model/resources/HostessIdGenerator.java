package cm.yowyob.letsgo.driver.domain.model.resources;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HexFormat;

public class HostessIdGenerator implements ResourceIdGenerator{

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public String generate(UserResource userResource) {

        final byte[] plain = new byte[4];
        SECURE_RANDOM.nextBytes(plain);

        return "H-" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + "" + HexFormat.of().formatHex(plain).toUpperCase();
    }


}
