/*
package cm.yowyob.letsgo.trip.application.controllers;

import cm.enspy.gi.project.trip_service.models.ScheduledTrip;
import cm.enspy.gi.project.trip_service.services.ScheduledTripService;
import cm.enspy.gi.project.trip_service.services.SetupScheduledTripRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/v0/scheduled-trips")
public class ScheduledTripController {

   @Autowired
   ScheduledTripService tripService;

   @PostMapping(value = "/create")
   public ScheduledTrip createScheduledTripPlanner(@RequestBody ScheduledTrip trip) throws Exception {

      return tripService.createScheduledTrip(trip);
   }

   @PutMapping(value = "/enable/{tripId}")
   public ScheduledTrip enableOrDisableScheduledTrip(@PathVariable String tripId,
         @RequestParam(defaultValue = "false") boolean enable) throws Exception {

      return tripService.enableOrDisableScheduledTrip(tripId, enable);
   }

   @PutMapping(value = "/setup-for-planner/{tripId}")
   public ScheduledTrip setUpScheduledTripForPlanner(@PathVariable String tripId,  @RequestParam(defaultValue = "false") boolean enable,
         @RequestBody SetupScheduledTripRequest request) throws Exception {

      return tripService.setUpScheduledTripForPlanner(tripId, enable, request);

   }

   @GetMapping(value = "/infos/{tripId}")
   public ScheduledTrip getScheduledTripInfos(@PathVariable String tripId) throws Exception  {

      return tripService.getScheduledTripInfos(tripId);

   }

   @PutMapping(value = "/terminate/{tripId}")
   public ScheduledTrip terminateById(String id) {

      return tripService.terminateScheduledTrip(id);

   }

   @PutMapping(value = "/change-driver/{tripId}/{driverId}")
   public ScheduledTrip changeDriver(String tripId, String driverId) throws Exception {

      return tripService.changeDriver(tripId, driverId);

   }

   @PutMapping(value = "/change-vehicule/{tripId}/{vehiculeId}")
   public ScheduledTrip changeVehicule(String tripId, String vehiculeId) throws Exception {

      return tripService.changeVehicule(tripId, vehiculeId);
   }

   @GetMapping(value = "/{tripId}")
   public ScheduledTrip getTripByScheduledId(String id) {

      return tripService.getTripByScheduledId(id);

   }

   @DeleteMapping(value = "/{tripId}")
   public ScheduledTrip deleteById(String id) {

      return tripService.deleteScheduledTrip(id);

   }

   @GetMapping(value = "")
   public List<ScheduledTrip> getAllScheduledTrip() {

      return tripService.getAllScheduledTrip();

   }

}
*/
