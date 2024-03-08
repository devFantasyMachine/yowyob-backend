package cm.yowyob.auth.app.domain.model;


import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.UUID;

public class HexIdGenerator {


    private static final int SALT_SIZE = 8;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final HexFormat format = HexFormat.of();
    private static final HexFormat fingerPrintFormat = HexFormat.ofDelimiter(":");


    public static String generate(Integer len) {

        final byte[] bytes = new byte[len == null ? SALT_SIZE : len];
        SECURE_RANDOM.nextBytes(bytes);

        return format.formatHex(bytes) + format.formatHex(UUID.randomUUID().toString().getBytes());
    }

    public static String generateFingerPrint() {

        final byte[] bytes = new byte[SALT_SIZE];
        SECURE_RANDOM.nextBytes(bytes);
        return fingerPrintFormat.formatHex(bytes) + fingerPrintFormat.formatHex(UUID.randomUUID().toString().getBytes());

    }



    public static String generate() {

        return generate(null);
    }



}

