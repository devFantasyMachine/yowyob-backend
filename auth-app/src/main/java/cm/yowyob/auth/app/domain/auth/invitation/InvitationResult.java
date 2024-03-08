package cm.yowyob.auth.app.domain.auth.invitation;


import cm.yowyob.auth.app.domain.model.Invitation;
import cm.yowyob.auth.app.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class InvitationResult {

    private User user;
    private Invitation invitation;
}
