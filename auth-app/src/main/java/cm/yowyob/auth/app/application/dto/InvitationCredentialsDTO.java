package cm.yowyob.auth.app.application.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationCredentialsDTO {

    private String invitationId;
    private String challenge;
}
