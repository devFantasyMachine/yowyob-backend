package cm.yowyob.auth.app.domain.model.code;


import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.UUID;


public class FingerPrintGenerator extends CodeGenerator<FingerPrint>{

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final HexFormat format = HexFormat.ofDelimiter(":");


    public FingerPrintGenerator(Integer defaultLength) {
        super(defaultLength);
    }

    @Override
    protected FingerPrint internalGenerateRandom(Integer length) {
        final byte[] bytes = new byte[length];
        SECURE_RANDOM.nextBytes(bytes);

        return new FingerPrint(format.formatHex(bytes) + format.formatHex(UUID.randomUUID().toString().getBytes()));
    }


}
