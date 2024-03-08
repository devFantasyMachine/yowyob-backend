package cm.yowyob.auth.app.domain.model.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserState {

    private UUID id;
    @NonNull
    private State state;
    private StateReason reason;
    private LocalDateTime instant;
    private UserId author;


    public static UserState getInitial(UserId author,@NonNull LocalDateTime instant){

        return new UserState(UUID.randomUUID(), State.PENDING, StateReason.ACCOUNT_VERIFICATION, instant, author);
    }



    public enum State {

        LOCKED,
        LOCKED_DELETED,
        DELETED,
        PENDING,
        ACTIVE
    }

    public static enum StateReason {

        ADMIN_ACTION, ACCOUNT_VERIFICATION, ACCEPT_VERIFICATION

    }


}
