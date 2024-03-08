package cm.yowyob.auth.app.application.controllers;


import cm.yowyob.auth.app.application.dto.ProfileDTO;
import cm.yowyob.auth.app.application.dto.UserPrivateDTO;
import cm.yowyob.auth.app.application.utils.HttpHelper;
import cm.yowyob.auth.app.domain.exceptions.UserNotFoundException;
import cm.yowyob.auth.app.domain.handlers.UserManager;
import cm.yowyob.auth.app.domain.model.user.Profile;
import cm.yowyob.auth.app.domain.model.user.UserId;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("users")
public class UserController {


    @Autowired
    UserManager userManager;


    @GetMapping("/me")
    public ResponseEntity<UserPrivateDTO> getUser(Authentication authentication,
                                                     HttpServletRequest request) {

        UUID tenantId = HttpHelper.getTenantId(request);
        UserId userId = UserId.of(authentication.getName());

        if (tenantId == null || userId == null)
            return ResponseEntity.badRequest()
                    .build();

        return ResponseEntity.of(userManager.getByTenantIdAndUserId(tenantId, userId)
                .map(UserPrivateDTO::from)
        );
    }


    @PutMapping("/me/profile")
    public ResponseEntity<ProfileDTO> updateMyProfile(Authentication authentication,
                                                          HttpServletRequest request,
                                                          @RequestBody ProfileDTO profileDTO)
            throws UserNotFoundException {

        UUID tenantId = HttpHelper.getTenantId(request);
        UserId userId = UserId.of(authentication.getName());

        if (tenantId == null || userId == null || profileDTO == null)
            return ResponseEntity.badRequest()
                    .build();

        Profile profile = userManager.updateUserProfile(tenantId, userId, profileDTO.toDomain());
        return ResponseEntity.ofNullable(ProfileDTO.from(profile));
    }





}
