package cm.yowyob.auth.app.domain.model.registration;


import cm.yowyob.auth.app.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegistrationResult {

    private User user;
    private String redirectUri;
}
