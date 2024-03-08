package cm.yowyob.auth.app.domain.model.tenant;

import java.util.Objects;
import java.util.regex.Pattern;

public class PasswordValidator {


    private static final Pattern GLOBAL =
            Pattern.compile("^([A-Za-zÀ-ÿ]|(-)|(_)|(#)|(@)|[0-9])*$");

    private static final Pattern WITH_NUMBER =
            Pattern.compile("^([A-Za-zÀ-ÿ]|(-)|(_)|)*([0-9])+([A-Za-zÀ-ÿ]|(-)|(_)|[0-9]|)*$");

    private static final Pattern WITH_SPECIAL_CHARACTERS =
            Pattern.compile("^([A-Za-z]|(-)|(_)|)*([À-ÿ])+([A-Za-zÀ-ÿ]|(-)|(_)|[0-9]|)*$");



    private final TenantPasswordConfig tenantPasswordConfig;

    public PasswordValidator(TenantPasswordConfig tenantPasswordConfig) {
        this.tenantPasswordConfig = Objects.requireNonNull(tenantPasswordConfig);
    }

    public boolean isNonValid(String password) {

        PasswordValidationRules rules = tenantPasswordConfig.getPasswordValidationRules();

        if (password == null) {
            return true;
        }

        if (password.length() < rules.getMinLength()
                || password.length() > rules.getMaxLength() )
            return true;

        if (!GLOBAL.matcher(password).matches())
            return true;

        if (!rules.isRequireNumber() && WITH_NUMBER.matcher(password).matches())
            return true;

        return !rules.isRequireNonAlpha() && WITH_SPECIAL_CHARACTERS.matcher(password).matches();
    }


}
