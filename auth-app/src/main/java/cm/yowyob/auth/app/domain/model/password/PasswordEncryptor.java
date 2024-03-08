package cm.yowyob.auth.app.domain.model.password;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

public interface PasswordEncryptor {

    Pattern Base64SaltPattern = Pattern.compile("^[A-Za-z0-9+/]+=*$");

    int defaultFactor();

    String encrypt(String var1, String var2, int var3);

    default String generateSalt() {
        ByteBuffer buf = ByteBuffer.allocate(32);
        UUID first = UUID.randomUUID();
        buf.putLong(first.getLeastSignificantBits());
        buf.putLong(first.getMostSignificantBits());
        UUID second = UUID.randomUUID();
        buf.putLong(second.getLeastSignificantBits());
        buf.putLong(second.getMostSignificantBits());
        return Base64.getEncoder().encodeToString(buf.array());
    }

    default String pluginDisplayName() {
        return null;
    }

    default boolean validateSalt(String salt) {
        try {
            Base64.getDecoder().decode(salt.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (Exception var3) {
            return false;
        }
    }
}
