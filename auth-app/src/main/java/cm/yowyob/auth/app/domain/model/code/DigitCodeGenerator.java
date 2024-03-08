package cm.yowyob.auth.app.domain.model.code;

import java.security.SecureRandom;

public class DigitCodeGenerator extends CodeGenerator<DigitCode>{

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public DigitCodeGenerator() {
        this(6);
    }

    public DigitCodeGenerator(int len) {
        super(len);
    }

    @Override
    protected DigitCode internalGenerateRandom(Integer length) {

        int size = Double.valueOf(Math.pow(10, (length - 1)))
                .intValue();

        return new DigitCode(size + SECURE_RANDOM.nextInt(9*size));
    }


}
