package cm.yowyob.auth.app.domain.model.code;

import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.UUID;

public class HexCodeGenerator extends CodeGenerator<HexCode> {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final HexFormat format = HexFormat.of();

    public HexCodeGenerator() {
        super(8);
    }

    @Override
    protected HexCode internalGenerateRandom(Integer length) {

        final byte[] bytes = new byte[length];
        SECURE_RANDOM.nextBytes(bytes);

        return new HexCode(format.formatHex(bytes) + format.formatHex(UUID.randomUUID().toString().getBytes()));
    }


}
