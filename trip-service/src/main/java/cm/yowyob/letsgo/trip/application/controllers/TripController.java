/*
package cm.yowyob.letsgo.trip.application.controllers;

import cm.enspy.gi.project.trip_service.models.Trip;
import cm.enspy.gi.project.trip_service.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/v0/trips")
public class TripController {

   @Autowired
   TripService tripService;

   @GetMapping(value = "")
   public ResponseEntity<List<Trip>> getTrips() {

      return ResponseEntity.ok(tripService.getAllTrip());
   }

   @PostMapping(value = "/create-trip/{scheduledId}")
   public ResponseEntity<Trip> createTrip(@PathVariable String scheduledId) throws Exception {

      return ResponseEntity.ok(tripService.startTrip(scheduledId));
   }

*/
/*    @GetMapping(value = "{userId}")
   public ResponseEntity<Trip> getUser(@PathVariable String userId) {

      return ResponseEntity.of(tripService.getTripById(userId));
   }

   @PostMapping(value = "start-trip/")
   public ResponseEntity<Trip> createUser(@RequestBody OwnerPreferences request) {

      return ResponseEntity.ok(tripService.startTrip(request));

   }

   @DeleteMapping(value = "{userId}")
   public ResponseEntity<Boolean> deleteUser(@PathVariable String userId) {

      return ResponseEntity.ok(tripService.deleteOwnerPreferences(userId));
   }
 *//*




}

*/
