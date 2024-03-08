package cm.yowyob.auth.app.application.controllers;


import cm.yowyob.auth.app.application.dto.TenantPublicDTO;
import cm.yowyob.auth.app.application.utils.HttpHelper;
import cm.yowyob.auth.app.domain.api.TenantCreationRequest;
import cm.yowyob.auth.app.domain.exceptions.TenantAlreadyExistsException;
import cm.yowyob.auth.app.domain.exceptions.TenantNotFoundException;
import cm.yowyob.auth.app.domain.exceptions.UserNotFoundException;
import cm.yowyob.auth.app.domain.handlers.TenantManager;
import cm.yowyob.auth.app.domain.model.tenant.Organisation;
import cm.yowyob.auth.app.domain.model.tenant.Tenant;
import cm.yowyob.auth.app.domain.model.user.UserId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("tenants")
public class TenantController {


    @Autowired
    TenantManager tenantManager;


    @GetMapping("/me")
    public ResponseEntity<TenantPublicDTO> getTenant(HttpServletRequest request) {

        UUID tenantId = HttpHelper.getTenantId(request);

        return ResponseEntity.of(tenantManager.getTenant(tenantId)
                .map(TenantPublicDTO::from)
        );
    }


    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantPublicDTO> getTenant(@PathVariable @NotNull UUID tenantId) {

        return ResponseEntity.of(tenantManager.getTenant(tenantId)
                .map(TenantPublicDTO::from)
        );
    }


    @PostMapping("")
    public TenantPublicDTO createTenant(Authentication authentication,
                                        @RequestBody @NotNull TenantCreationRequest request)
            throws UserNotFoundException, TenantAlreadyExistsException {

        UserId userId = new UserId(authentication.getName());

        Tenant tenant = tenantManager.createTenant(userId, request);

        return TenantPublicDTO.from(tenant);
    }


    @PutMapping("/{tenantId}")
    public TenantPublicDTO updateTenant(@PathVariable @NotNull UUID tenantId,
                                        @RequestBody @NotNull Organisation organisation)
            throws TenantNotFoundException {

        Tenant tenant = tenantManager.updateTenantInfo(tenantId, organisation);
        return TenantPublicDTO.from(tenant);
    }


    @ExceptionHandler(value = TenantNotFoundException.class)
    public ResponseEntity<?> onTenantNotFoundException(TenantNotFoundException exception) {

        return ResponseEntity.notFound()
                .build();
    }


    @ExceptionHandler(value = TenantAlreadyExistsException.class)
    public ResponseEntity<?> onTenantAlreadyExistsException(TenantAlreadyExistsException exception) {

        return ResponseEntity.notFound()
                .build();
    }


}
