package cm.yowyob.auth.app.domain.model.code;


import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class Base64StringGenerator extends CodeGenerator<Base64String> {


    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public Base64StringGenerator(Integer defaultLength) {
        super(defaultLength);
    }

    @Override
    protected Base64String internalGenerateRandom(Integer length) {

        final byte[] bytes = new byte[length];
        SECURE_RANDOM.nextBytes(bytes);

        String partOne = Base64.getUrlEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        String encodeToString = Base64.getUrlEncoder().encodeToString(bytes);
        return new Base64String(partOne + encodeToString);
    }


}
