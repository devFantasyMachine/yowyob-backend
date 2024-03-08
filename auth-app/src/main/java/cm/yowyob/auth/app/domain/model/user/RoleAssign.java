package cm.yowyob.auth.app.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
@Builder
@AllArgsConstructor
public class RoleAssign {

    private final Role role;
    private final User user;
    private final String enableReason;

    private Boolean isEnabled;
    private LocalDateTime disabledAt;
    private String disableReason;

    private final LocalDateTime issueAt;
    private final LocalDateTime expireAt;


    public RoleAssign disable(String reason){
        Objects.requireNonNull(reason, "reason is required");

        return RoleAssign.builder()
                .disabledAt(LocalDateTime.now())
                .disableReason(reason)
                .isEnabled(false)
                .role(role)
                .user(user)
                .enableReason(enableReason)
                .issueAt(issueAt)
                .expireAt(expireAt)
                .build();

    }



}
