package cm.yowyob.auth.app.application.dto;


import cm.yowyob.auth.app.domain.model.Invitation;
import cm.yowyob.auth.app.domain.model.InvitationReason;
import cm.yowyob.auth.app.domain.model.contacts.Contact;
import lombok.*;

import java.time.Instant;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDTO {

    private Contact contact;
    private String invitationId;
    private UserPublicDTO sender;
    private InvitationReason subject;
    private Boolean canResend;
    private Instant issueAt;
    private Instant expireAt;
    private Boolean isNonUsed;
    private Boolean isAccepted;
    private Instant acceptedAt;

    public static InvitationDTO from(Invitation invitation) {
        return InvitationDTO.builder()
                .sender(UserPublicDTO.form(invitation.getSender()))
                .invitationId(invitation.getId())
                .contact(invitation.getEmailAddress())
                .subject(invitation.getReason())
                .issueAt(invitation.getIssueAt())
                .expireAt(invitation.getExpireAt())
                .isNonUsed(invitation.isNonUsed())
                .isAccepted(invitation.getAccepted())
                .acceptedAt(invitation.getAcceptedAt())
                .build();
    }


}
