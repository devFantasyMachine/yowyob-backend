/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.controllers;


import cm.yowyob.letsgo.trip.application.dto.*;
import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.managers.PlannerPlanManager;
import cm.yowyob.letsgo.trip.domain.managers.PublishManager;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlan;
import cm.yowyob.letsgo.trip.domain.model.plan.core.PlannerPlanRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.annotation.Nonnull;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/v0/planner-plans")
public class PlannerPlanController {


    @Autowired
    PlannerPlanManager plannerPlanManager;

    @Autowired
    PublishManager publishManager;



    @GetMapping(value = "/{tripPlanId}")
    public PlannerPlanDTO getPlannerPlannedTrip (
            @PathVariable UUID tripPlanId) throws PlannedTripNotFoundException {

        PlannerPlan plannerPlan = plannerPlanManager.getPlannerPlan(tripPlanId);

        return PlannerPlanDTO.mapFrom(plannerPlan);
    }



    @GetMapping(value = "")
    public Set<PlannerPlanDTO> getAllByPlannerId (Authentication authentication) {

        Set<PlannerPlan> plannerPlans = plannerPlanManager.getByPlannerId(authentication.getName());

        return plannerPlans.stream()
                .map(PlannerPlanDTO::mapFrom)
                .collect(Collectors.toSet());
    }




    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PlannerPlanDTO createPlannerPlan(
            Authentication authentication, @RequestBody @Valid PlannerPlanRequest request)

            throws PlannerNotFoundException, DriverNotFoundException,
            VehicleNotFoundException, NoSuchPrincipalItineraryException, DraftTripNotFoundException {

        PlannerPlan plannerPlan = plannerPlanManager.createPlannerPlan(authentication.getName(), request);

        return PlannerPlanDTO.mapFrom(plannerPlan);
    }












    @PutMapping(value = "/setup-plan", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Setup Plan", notes = "EndPoint to setup a trip for planner by draft")
    public PlannerPlanDTO setupPlannedTrip(
            @ApiParam(value = "requestDTO", required = true)
            @RequestBody @Valid @NonNull SetupPolicyDTO requestDTO)

            throws PlannedTripNotFoundException, IllegalSetupPolicyException {

        PlannerPlanRequest request = PlannerPlanRequest.builder()
                .cancellationPolicy(requestDTO.getCancellationPolicy())
                .confirmationPolicy(requestDTO.getConfirmPolicy())
                .coursePolicy(requestDTO.getCoursePolicy())
                .reservationPolicy(requestDTO.getReservationPolicy())
                .build();

        PlannerPlan plannerPlan =
                plannerPlanManager.setupPolicy(requestDTO.getTripPlanId(), request);

        return PlannerPlanDTO.mapFrom(plannerPlan);
    }


    @PutMapping(value = "/extends-itinerary/{tripId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PlannerPlanDTO extendItinerary(@PathVariable String tripId,
                                          @RequestBody @Valid @NonNull ExtendItineraryDTO requestDTO)

            throws PlannedTripNotFoundException, IllegalOperationOnStaticItineraryPlanException,
            IllegalReplaceLegOperationException, LegNotFoundException {

        PlannerPlan plannerPlan =
                plannerPlanManager.extendsItinerary(tripId,
                        requestDTO.getNewStopLocation().toStopLocation(),
                        Duration.ofSeconds(requestDTO.getStopDuration()));

        return PlannerPlanDTO.mapFrom(plannerPlan);
    }


    @PutMapping(value = "/split-leg/{tripId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PlannerPlanDTO splitLeg(@PathVariable String tripId,
                                          @RequestBody @Valid @NonNull SplitLegDTO requestDTO)

            throws PlannedTripNotFoundException, IllegalOperationOnStaticItineraryPlanException {

        PlannerPlan plannerPlan =
                plannerPlanManager.splitLeg(tripId,
                        requestDTO.getLegIndex(),
                        requestDTO.getIntermediateStop().toStop());

        return PlannerPlanDTO.mapFrom(plannerPlan);
    }


    @PutMapping(value = "/planner-plan/change-driverinfos-vehicleinfos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PlannerPlanDTO changeVehiclesAndDrivers(
            @RequestBody @Valid @Nonnull ChangeVehiclesAndDriversDTO requestDTO)

            throws PlannedTripNotFoundException, DriverNotFoundException, VehicleNotFoundException, IllegalReplaceLegOperationException, LegNotFoundException {

        PlannerPlan plannerPlan =
                plannerPlanManager.changeVehiclesAndDrivers(requestDTO.getTripPlanId(),
                        requestDTO.getVehicleMapIds(),
                        requestDTO.getDriverMapIds());

        return PlannerPlanDTO.mapFrom(plannerPlan);
    }

    @PutMapping(value = "/{tripId}/publish", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Setup Plan", notes = "EndPoint to setup a trip for planner by draft")
    public PlannerPlanDTO publishPlannerTripPlan(@PathVariable UUID tripId)

            throws PlannedTripNotFoundException, IllegalPublishNonPlannedTripException, UnPublishableTripException, NoSuchPrincipalItineraryException, PublishedTripNotFoundException {

        PlannerPlan plannerPlan = publishManager.publishPlannerTripPlan(tripId);

        return PlannerPlanDTO.mapFrom(plannerPlan);
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
