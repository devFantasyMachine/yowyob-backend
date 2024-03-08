/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.controllers;


import cm.yowyob.letsgo.trip.application.dto.PoolerPlanDTO;
import cm.yowyob.letsgo.trip.application.dto.PoolerPlanRequestDTO;
import cm.yowyob.letsgo.trip.domain.api.PoolerTripPlanRecords;
import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.managers.PublishManager;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PoolerPlanRequest;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.publish.SharedPolicy;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v0/pooler-plans")
public class PoolerPlanController {


    @Autowired
    PoolerTripPlanRecords poolerTripPlanRecords;

    @Autowired
    PublishManager publishManager;



    @GetMapping(value = "/{tripPlanId}")
    public PoolerPlanDTO getPoolerPlannedTrip (
            @PathVariable UUID tripPlanId) throws PoolerPlanNotFoundException {

        PoolerPlan poolerPlan = poolerTripPlanRecords.getPoolerPlan(tripPlanId);

        return PoolerPlanDTO.mapFrom(poolerPlan);
    }



    @GetMapping(value = "")
    public Set<PoolerPlanDTO> getAllByPoolerId (Authentication authentication) {

        Set<PoolerPlan> poolerPlans = poolerTripPlanRecords.getByPoolerId(authentication.getName());

        return poolerPlans.stream()
                .map(PoolerPlanDTO::mapFrom)
                .collect(Collectors.toSet());
    }



    @PostMapping(value = "", produces = { "application/hal+json" })
    public PoolerPlanDTO createPoolerPlan(Authentication authentication,
            @RequestBody @Valid PoolerPlanRequestDTO request)
            throws VehicleNotFoundException, PollerNotFoundException,
            NoSuchPrincipalItineraryException, DraftTripNotFoundException {

        PoolerPlanRequest planRequest = PoolerPlanRequest.builder()
                .totalCost(request.getTotalCost())
                .transportModes(request.getTransportModes())
                .vehicleId(request.getVehicleId())
                .draftId(request.getDraftId())
                .draftTrip(request.getDraftTrip())
                .departure(request.getDeparture())
                .luggageBoxQuantity(request.getLuggageBoxQuantity())
                .luggageCost(request.getLuggageCost())
                .seatCost(request.getSeatCost())
                .seatCount(request.getSeatCount())
                .paymentSetting(request.getPaymentSetting() == null ? null : request.getPaymentSetting().toPaymentSetting())
                .build();

        PoolerPlan poolerPlan = poolerTripPlanRecords.createPoolerPlan(authentication.getName(), planRequest);

        return PoolerPlanDTO.mapFrom(poolerPlan);
    }



    @PutMapping(value = "/{tripPlanId}")
    public PoolerPlanDTO updatePoolerPlan(Authentication authentication,
                                          @PathVariable UUID tripPlanId,
                                          @RequestBody @Valid PoolerPlanRequestDTO request)
            throws VehicleNotFoundException, NoSuchPrincipalItineraryException,
            IllegalUpdateNonPlannedTripException, PoolerPlanNotFoundException, UnauthorizedTripAccessException {

        PoolerPlanRequest planRequest = PoolerPlanRequest.builder()
                .totalCost(request.getTotalCost())
                .transportModes(request.getTransportModes())
                .vehicleId(request.getVehicleId())
                .draftId(request.getDraftId())
                .draftTrip(request.getDraftTrip())
                .departure(request.getDeparture())
                .luggageBoxQuantity(request.getLuggageBoxQuantity())
                .luggageCost(request.getLuggageCost())
                .seatCost(request.getSeatCost())
                .seatCount(request.getSeatCount())
                .paymentSetting(request.getPaymentSetting() == null ? null : request.getPaymentSetting().toPaymentSetting())
                .build();

        PoolerPlan poolerPlan = poolerTripPlanRecords.updatePoolerPlan(authentication.getName(), tripPlanId, planRequest);

        return PoolerPlanDTO.mapFrom(poolerPlan);
    }



    @PutMapping(value = "/{tripPlanId}/itinerary")
    public PoolerPlanDTO updateItinerary(Authentication authentication,
                                          @PathVariable UUID tripPlanId,
                                         @RequestBody List<Stop> intermediatesStops)
            throws PoolerPlanNotFoundException, UnauthorizedTripAccessException {

        PoolerPlan poolerPlan =
                poolerTripPlanRecords.updateItinerary(authentication.getName(), tripPlanId, intermediatesStops);

        return PoolerPlanDTO.mapFrom(poolerPlan);
    }

    @PutMapping(value = "/{tripId}/publish", produces = MediaType.APPLICATION_JSON_VALUE)
    public PoolerPlanDTO publishPlannerTripPlan(@PathVariable UUID tripId, @RequestBody SharedPolicy sharedPolicy)

            throws IllegalPublishNonPlannedTripException, UnPublishableTripException, NoSuchPrincipalItineraryException, PublishedTripNotFoundException, PoolerPlanNotFoundException {

        PoolerPlan poolerPlan = publishManager.publishPoolerTripPlan(tripId, sharedPolicy);

        return PoolerPlanDTO.mapFrom(poolerPlan);
    }



    @ExceptionHandler(value = PlannedTripNotFoundException.class)
    public ResponseEntity<?> onPlannedTripNotFoundException(){

        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "TRIP_PLAN_NOT_FOUND"))
                .build();
    }


    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ResponseEntity<?> onUnexpectedTypeException(){

        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "INVALID_INPUT"))
                .build();
    }


}
