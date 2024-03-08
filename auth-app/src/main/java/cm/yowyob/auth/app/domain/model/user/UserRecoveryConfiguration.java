package cm.yowyob.auth.app.domain.model.user;


import cm.yowyob.auth.app.domain.model.contacts.EmailAddress;
import cm.yowyob.auth.app.domain.model.contacts.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRecoveryConfiguration {

    public static final UserRecoveryConfiguration DEFAULT = new UserRecoveryConfiguration();

    private boolean enabled;
    private UserId recoveryAccountId;
    private EmailAddress recoveryEmailAddress;
    private PhoneNumber recoveryPhoneNumber;
    private Map<String, String> questionsAnswers;
    private final List<String> recoveryCodes = new ArrayList<>();
}

