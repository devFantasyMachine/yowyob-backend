/*

package cm.yowyob.letsgo.trip.application.controllers;


import cm.yowyob.letsgo.trip.domain.model.resources.PlannedSeat;
import cm.yowyob.letsgo.trip.domain.model.resources.ResourceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/api/v0/places/")
public class PlaceController {

   @Autowired
   PassengerPlaceManager passengerPlaceManager;


   @PutMapping(value = "{tripId}/{plannerId}/mark-place-unavailable")
   public ResponseEntity<Set<PlannedSeat>> makePlaceUnavailable(@PathVariable(name = "tripId") UUID tripId,
                                                                @PathVariable(name = "plannerId") String plannerId,
                                                                @RequestBody Set<String> placesIds)  {

      return ResponseEntity.ok(passengerPlaceManager.modifyStatus(tripId, plannerId, placesIds, ResourceStatus.UNAVAILABLE));
   }



   @PutMapping(value = "{tripId}/{plannerId}/mark-place-available")
   public ResponseEntity<Set<PlannedSeat>> makePlaceAvailable(@PathVariable(name = "tripId") UUID tripId,
                                                              @PathVariable(name = "plannerId") String plannerId,
                                                              @RequestBody Set<String> placesIds ) {

      return ResponseEntity.ok(passengerPlaceManager.modifyStatus(tripId, plannerId, placesIds, ResourceStatus.FREE));
   }


   @PutMapping(value = "{planId}/{plannerId}/update-cost")
   public ResponseEntity<Set<PlannedSeat>> updatePassengerPlaceCost(@PathVariable(name = "tripId") UUID planId,
                                                                    @PathVariable(name = "plannerId") String plannerId,
                                                                    @RequestParam("cost") Float cost,
                                                                    @RequestBody Set<String> placesIds) {

      return ResponseEntity.ok(passengerPlaceManager.modifyPlacesCost(planId, plannerId, placesIds, cost));
   }





 
 

}

*/
