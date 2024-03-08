package cm.yowyob.auth.app.domain.model.user;



import cm.yowyob.auth.app.domain.TwoFactorAuthChallenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTwoFactorConfiguration {


    public static final UserTwoFactorConfiguration DEFAULT = new UserTwoFactorConfiguration();

    private final List<TwoFactorAuthChallenge> methods = new ArrayList<>();
    private boolean enabled;

    public TwoFactorAuthChallenge getLastUsedMethod() {
        return this.methods.size() == 1 ?
                this.methods.get(0) : this.methods.stream()
                .filter((var0) -> var0.getLastUsed() != null && var0.getLastUsed())
                .findFirst()
                .orElse(null);
    }


    public TwoFactorAuthChallenge getMethodById(String var1) {

        return this.methods
                .stream()
                .filter((var0) -> var0.getId() != null)
                .filter((var1x) -> var1x.getId().equals(var1))
                .findFirst()
                .orElse(null);
    }


    public UserTwoFactorConfiguration secure() {
        this.methods.forEach(TwoFactorAuthChallenge::secure);
        return this;
    }


}
