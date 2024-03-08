package cm.yowyob.auth.app.application.controllers;


import cm.yowyob.auth.app.application.dto.InvitationDTO;
import cm.yowyob.auth.app.domain.exceptions.InvitationNotFoundException;
import cm.yowyob.auth.app.domain.handlers.InvitationManager;
import cm.yowyob.auth.app.domain.model.Invitation;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitations")
public class InvitationController {

    @Autowired
    InvitationManager invitationManager;



    @GetMapping("/{id}")
    public InvitationDTO getInvitation(@PathVariable @NotNull String id)
            throws InvitationNotFoundException {

        Invitation invitation = invitationManager.getInvitation(id);
        return InvitationDTO.from(invitation);
    }




    @ExceptionHandler(value = InvitationNotFoundException.class)
    public ResponseEntity<?> onInvitationNotFoundException(InvitationNotFoundException exception){

        return ResponseEntity.notFound()
                .build();
    }

}
