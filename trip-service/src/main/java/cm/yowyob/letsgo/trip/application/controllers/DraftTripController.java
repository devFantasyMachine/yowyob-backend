package cm.yowyob.letsgo.trip.application.controllers;


import cm.yowyob.letsgo.trip.application.dto.DraftTripDTO;
import cm.yowyob.letsgo.trip.application.dto.DraftTripIOMapper;
import cm.yowyob.letsgo.trip.domain.exceptions.DraftTripNotFoundException;
import cm.yowyob.letsgo.trip.domain.exceptions.UnauthorizedDraftTripAccessException;
import cm.yowyob.letsgo.trip.domain.managers.DraftTripManager;
import cm.yowyob.letsgo.trip.domain.model.DraftTrip;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;




@RestController
@RequestMapping("/api/v0/drafts")
public class DraftTripController {


    private final DraftTripIOMapper draftTripIOMapper = new DraftTripIOMapper();


    @Autowired
    DraftTripManager draftTripManager;

    // TODO: 20/08/2023 add dto

    @GetMapping(value = "")
    public List<DraftTrip> getAllUserDraftTrip (Authentication authentication) {

        return draftTripManager.getUserDraftTrips(authentication.getName());
    }


    @GetMapping(value = "/{draftId}")
    public DraftTrip getDraftTrip (Authentication authentication, @PathVariable String draftId)
            throws DraftTripNotFoundException, UnauthorizedDraftTripAccessException {

        return draftTripManager.getDraftTrip(authentication.getName(), draftId);
    }


    @PostMapping(value = "")
    public DraftTrip createUserDraftTrip (Authentication authentication,
                                          @RequestBody @Valid DraftTripDTO request) {

        DraftTrip draftTrip = draftTripIOMapper.toObject(request);

        return draftTripManager.createDraftTrip(draftTrip, authentication.getName());
    }


    @PutMapping(value = "/{draftId}")
    public DraftTrip getDraftTrip (Authentication authentication,
                                   @PathVariable UUID draftId,
                                   @RequestBody DraftTripDTO request)
            throws DraftTripNotFoundException, UnauthorizedDraftTripAccessException {

        DraftTrip draftTrip = draftTripIOMapper.toObject(request);

        return draftTripManager.updateDraftTrip(draftTrip,authentication.getName(), draftId);
    }



}
