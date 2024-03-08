package cm.yowyob.auth.app.domain.model.tenant;

import java.util.Objects;
import java.util.regex.Pattern;

public class UsernameValidator {


    private static final Pattern GLOBAL =
            Pattern.compile("^([A-Za-zÀ-ÿ]|(-)|(_)|[0-9])*$");

    private static final Pattern START_WITH_NUMBER =
            Pattern.compile("^([0-9])+([A-Za-zÀ-ÿ]|(-)|(_)|[0-9]|)*$");

    private static final Pattern WITH_NUMBER =
            Pattern.compile("^([A-Za-zÀ-ÿ]|(-)|(_)|)*([0-9])+([A-Za-zÀ-ÿ]|(-)|(_)|[0-9]|)*$");

    private static final Pattern WITH_SPECIAL_CHARACTERS =
            Pattern.compile("^([A-Za-z]|(-)|(_)|)*([À-ÿ])+([A-Za-zÀ-ÿ]|(-)|(_)|[0-9]|)*$");



    private final TenantUsernameConfig tenantUsernameConfig;

    public UsernameValidator(TenantUsernameConfig tenantUsernameConfig) {
        this.tenantUsernameConfig = Objects.requireNonNull(tenantUsernameConfig);
    }

    public boolean isNonValid(String username) {

        if (username == null)
            return true;

        if (username.length() < tenantUsernameConfig.getMinCharacters() || username.length() > tenantUsernameConfig.getMaxCharacters() )
            return true;

        if (!GLOBAL.matcher(username).matches())
            return true;

        if (!tenantUsernameConfig.getWithNumbers() && WITH_NUMBER.matcher(username).matches())
            return true;

        return !tenantUsernameConfig.getCanStartWithNumber() && START_WITH_NUMBER.matcher(username).matches();
    }


}
