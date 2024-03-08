/*

package cm.yowyob.letsgo.trip.application.controllers;


import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.managers.ReservationManager;
import cm.yowyob.letsgo.trip.domain.lock.LockRequest;
import cm.yowyob.letsgo.trip.domain.lock.LockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v0/reservations")
public class ReservationController {


    @Autowired
    ReservationManager reservationManager;



    @PostMapping(value = "/multiple-user/lock")
    public ResponseEntity<LockResponse> reserveMultiplePoolerTrip(@RequestBody LockRequest request)
            throws PublishedTripNotBookableException,
            PublishedTripNotFoundException, InvalidReservationRequestException {

        return ResponseEntity.ok(reservationManager.reserveResources(request));
    }

    @PostMapping(value = "/single-user/lock")
    public ResponseEntity<LockResponse> reserveSinglePoolerTrip(@RequestBody LockRequest request)
            throws PublishedTripNotBookableException,
            PublishedTripNotFoundException, InvalidReservationRequestException {

        return ResponseEntity.ok(reservationManager.reserveResources(request));
    }


    @PutMapping(value = "/confirm/{reservationId}/{token}")
    public Boolean confirm(@PathVariable("reservationId") String reservationId,
                                           @PathVariable("token") String token)
            throws ConfirmationTimeoutException, ConfirmationNotAuthorizedException,
            ReservationNotFoundException, PublishedTripNotFoundException, IllegalTripResourceUpdateException {

        return reservationManager.confirmResource(reservationId, token);
    }



    @PutMapping(value = "/cancel/{reservationId}/{token}")
    public Boolean cancelReservation(@PathVariable("reservationId") String reservationId,
                                                     @PathVariable("token") String token)
            throws ReservationNotFoundException, PublishedTripNotFoundException,
            CancellationNotAuthorizedException, IllegalCancelReservationException {

        return reservationManager.cancelReservation(reservationId, token);

    }


    @PutMapping(value = "/abort/{reservationId}/{token}")
    public void abortReservation(@PathVariable("reservationId") String reservationId,
                                     @PathVariable("token") String token)
            throws ConfirmationNotAuthorizedException,
            ReservationNotFoundException, PublishedTripNotFoundException, IllegalCancelReservationException {

        reservationManager.abortReservation(reservationId, token);

    }




}

*/
