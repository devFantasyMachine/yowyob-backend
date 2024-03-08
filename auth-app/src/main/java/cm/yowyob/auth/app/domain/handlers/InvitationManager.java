package cm.yowyob.auth.app.domain.handlers;


import cm.yowyob.auth.app.domain.auth.invitation.AcceptInvitationRequest;
import cm.yowyob.auth.app.domain.auth.invitation.InvitationResult;
import cm.yowyob.auth.app.domain.exceptions.InvalidChallengeException;
import cm.yowyob.auth.app.domain.exceptions.InvitationExpiredException;
import cm.yowyob.auth.app.domain.exceptions.InvitationNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.UserNotFoundException;
import cm.yowyob.auth.app.domain.model.Invitation;
import cm.yowyob.auth.app.domain.model.InvitationReason;
import cm.yowyob.auth.app.domain.model.code.DigitCodeGenerator;
import cm.yowyob.auth.app.domain.model.code.HexCodeGenerator;
import cm.yowyob.auth.app.domain.model.contacts.ContactFactory;
import cm.yowyob.auth.app.domain.model.contacts.EmailAddress;
import cm.yowyob.auth.app.domain.model.message.email.Email;
import cm.yowyob.auth.app.domain.model.registration.PasswordHelper;
import cm.yowyob.auth.app.domain.model.tenant.EmailConfiguration;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.user.*;
import cm.yowyob.auth.app.domain.port.GroupService;
import cm.yowyob.auth.app.domain.port.InvitationService;
import cm.yowyob.auth.app.domain.port.MessagingService;
import cm.yowyob.auth.app.domain.port.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@Builder
@AllArgsConstructor
public class InvitationManager {

    private final InvitationService invitationService;
    private final PasswordHelper passwordHelper;
    private final UserService userService;
    private final MessagingService messagingService;

    private final GroupService groupService;


    private final ContactFactory contactFactory = ContactFactory.getInstance();
    private final DigitCodeGenerator digitCodeGenerator = new DigitCodeGenerator();
    private final HexCodeGenerator hexCodeGenerator = new HexCodeGenerator();


    public Invitation getInvitation(String id) throws InvitationNotFoundException {

        return invitationService.getByInvitationId(id)
                .filter(Invitation::isNonUsed)
                .orElseThrow(InvitationNotFoundException::new);
    }


    public Invitation getValidInvitation(String id) throws InvitationNotFoundException, InvitationExpiredException {

        log.info("validate user account with invitation {}", id);

        Invitation invitation = getInvitation(id);

        log.info("retrieve invitation, {} successfully", invitation);

        if (invitation.isExpired()) {
            invitation.reject();
            invitationService.save(invitation);
            log.error("registration has expired");
            throw new InvitationExpiredException();
        }

        return invitation;

    }


    public Invitation save(Invitation invitation) {

        return invitationService.save(invitation);
    }


    public InvitationResult acceptInvitation(AcceptInvitationRequest request)
            throws InvalidChallengeException,
            InvitationNotFoundException,
            InvitationExpiredException, UserNotFoundException {

        Invitation validInvitation = getValidInvitation(request.getId());

        User invitedUser = validInvitation.getTarget();

        if (invitedUser == null || invitedUser.isLocked())
            throw new UserNotFoundException();

        userService.save(invitedUser.activate(UserState.StateReason.ACCEPT_VERIFICATION, null));

        User activatedUser = userService.save(invitedUser);

        if (validInvitation.getReason() == InvitationReason.JOIN_GROUP) {

            Group group = validInvitation.getGroup();

            GroupMember groupMember = new GroupMember(activatedUser);
            group.addMember(groupMember);

            groupService.save(groupMember);
            groupService.save(group);

            activatedUser.addRoles(group.getRolesAsStringList());
        }

        activatedUser.removeAction(RequireAction.VALIDATE_REGISTRATION);
        User savedUser = userService.save(activatedUser);

        validInvitation.accept();
        invitationService.save(validInvitation);

        return InvitationResult.builder()
                .invitation(validInvitation)
                .user(savedUser)
                .build();
    }



    public void sendInvitation(@NonNull Invitation savedInvitation,
                               @NonNull Tenant tenant) {

        String txt = "Verify your account with this link: http://" + tenant.getIssuer() + "/invitation?token=" + savedInvitation.getId();

        EmailConfiguration emailConfiguration = tenant.getEmailConfiguration();
        EmailAddress emailAddress = savedInvitation.getEmailAddress();

        Email email = Email.builder()
                .to(List.of(emailAddress))
                .from((EmailAddress) contactFactory.createEmailAddress(emailConfiguration.defaultFromEmail))
                .subject("Account Verification")
                .text(txt)
                .build();

        log.info("invitation sent");
        messagingService.sendEmail(email, emailConfiguration);
        log.info("invitation {} has been saved successfully", savedInvitation);
    }



}
