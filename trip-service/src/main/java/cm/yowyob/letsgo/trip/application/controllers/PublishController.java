/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 *//*


package cm.yowyob.letsgo.trip.application.controllers;


import cm.yowyob.letsgo.trip.application.dto.*;
import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.managers.PublishManager;
import cm.yowyob.letsgo.trip.domain.model.publish.CheckerProblem;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/v0/publish")
public class PublishController {


    @Autowired
    PublishManager publishManager;



    @PutMapping("/{tripPlanId}/{publisherId}")
    PublishedTripDTO publishAgencyTrip(@PathVariable("tripPlanId") UUID tripPlanId)
            throws UnPublishableTripException, PlannedTripNotFoundException,
            NoSuchPrincipalItineraryException, PublishedTripNotFoundException,
            IllegalPublishNonPlannedTripException {

        PublishedPlannerTrip publishedTrip = publishManager.publishTripPlan(tripPlanId);

        return PublishedTripDTO.fromPublishTrip(publishedTrip);
    }




    @GetMapping(value = "{publishTripId}")
    PublishedTripDTO getPublishedTrip (
            @PathVariable String publishTripId)

            throws PublishedTripNotFoundException {

        PublishedPlannerTrip publishedTrip = publishManager.getPublishedTrip(publishTripId);

        return PublishedTripDTO.fromPublishTrip(publishedTrip);
    }



    @GetMapping(value = "safe/{publishTripId}")
    TripPlanReservationResponse getSafePublishedTrip (
            @PathVariable String publishTripId)

            throws PublishedTripNotFoundException {

        PublishedPlannerTrip publishedTrip = publishManager.getPublishedTrip(publishTripId);

        return TripPlanReservationResponse.builder()
                .publishAt(publishedTrip.getPublishedAt())
                .publishTripId(publishTripId)
                .cancellationPolicyDTO(CancellationPolicyDTO.fromCancellationPolicy(publishedTrip.cancellationPolicy()))
                .confirmationPolicyDTO(ConfirmationPolicyDTO.fromConfirmationPolicy(publishedTrip.confirmationPolicy()))
                .paymentSettingDTO(PaymentSettingDTO.fromPayment(publishedTrip.getPlannedTrip().getPaymentSetting())  )
                .tripType(publishedTrip.getPlannedTrip().getTripType())
                .tripPrestige(publishedTrip.getPlannedTrip().getTripPrestige())
                .reservationPolicyDTO(ReservationPolicyDTO.fromReservationPolicy(publishedTrip.reservationPolicy()))
                .plannerInformationDTO(PlannerInformationDTO.fromPlannerInformation(publishedTrip.getPlannedTrip().getPlannerInformation()))
                .build();
    }


    @PutMapping("cancel/{tripPlanId}/{publisherId}")
    PublishedTripDTO cancelPublishedTrip(@PathVariable("tripPlanId") String tripPlanId,
                                       @PathVariable("publisherId") String publisherId)
            throws PublishedTripNotFoundException, UnauthorizedCancelTripException {

        PublishedPlannerTrip publishedTrip = publishManager.cancelPublishedTrip(tripPlanId, publisherId);

        return PublishedTripDTO.fromPublishTrip(publishedTrip);
    }


    @GetMapping(value = "planner/{plannerId}")
    Set<PublishedTripDTO> getAllByPlannerId (
            @PathVariable String plannerId) {

        Set<PublishedPlannerTrip> publishedTrips = publishManager.getByPlannerId(plannerId);

        return publishedTrips.stream()
                .map(PublishedTripDTO::fromPublishTrip)
                .collect(Collectors.toSet());
    }


    @GetMapping(value = "planner/{plannerId}/range")
    Set<PublishedTripDTO> getRangeByPlannerId (
            @PathVariable String plannerId, @RequestBody @Valid @NonNull RangeDateDTO rangeDateDTO) {

        Set<PublishedPlannerTrip> publishedTrips =
                publishManager.getRangeByPlannerId(plannerId, rangeDateDTO.getFrom(), rangeDateDTO.getTo());

        return publishedTrips.stream()
                .map(PublishedTripDTO::fromPublishTrip)
                .collect(Collectors.toSet());
    }









    @ExceptionHandler(value = PlannedTripNotFoundException.class)
    ResponseEntity<?> onPlannedTripNotFoundException(){

        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "TRIP_PLAN_NOT_FOUND"))
                .build();
    }


    @ExceptionHandler(value = UnPublishableTripException.class)
    public ResponseEntity<?> onUnPublishableTripException(UnPublishableTripException unPublishableTripException){

        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, unPublishableTripException.getCheckerProblems()
                        .stream()
                        .map(CheckerProblem::toString)
                        .collect(Collectors.toSet())
                        .toString()))
                .build();
    }


    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ResponseEntity<?> onUnexpectedTypeException(){

        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "INVALID_INPUT"))
                .build();
    }



}
*/
