/*
package cm.yowyob.letsgo.trip.application.controllers;

import cm.enspy.gi.project.trip_service.components.JobEngine;
import cm.enspy.gi.project.trip_service.models.JobStatus;
import cm.enspy.gi.project.trip_service.models.RoutineTrip;
import cm.enspy.gi.project.trip_service.repositories.RoutineTripRepository;
import cm.enspy.gi.project.trip_service.services.ScheduledTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/api/v0/routine-trips")
public class RoutineTripController {

   @Autowired
   ScheduledTripService tripService;

   @Autowired
   RoutineTripRepository routineTripRepository;

   @Autowired
   JobEngine jobEngine;



   @PostMapping(value = "/create-routine/{tripId}")
   public RoutineTrip createRoutine(@PathVariable String tripId,
         @RequestParam(defaultValue = "false") boolean enable, @RequestBody RoutineTrip routineTrip) throws Exception {

      
         routineTrip.setAddAt(LocalDateTime.now());
         routineTrip.setRoutineId(UUID.randomUUID());
         routineTrip.setIsEnabled(enable);
         routineTrip.setScheduledId(UUID.fromString(tripId));
         routineTrip.setJobStatus(JobStatus.UNSCHEDULED); 


         if(enable){

            jobEngine.scheduleNewJob(routineTrip);

         }

      return routineTripRepository.save(routineTrip);
   }


   @PutMapping(value = "/toggle/{routineId}")
   public RoutineTrip enableOrDisable(@PathVariable String routineId) throws Exception {

      RoutineTrip routineTrip = routineTripRepository.findByRoutineId(UUID.fromString(routineId));

      if(routineTrip.getIsEnabled()){

         jobEngine.pauseJob(routineTrip);

      }
      else if( routineTrip.getJobStatus()  == JobStatus.UNSCHEDULED){

         jobEngine.scheduleNewJob(routineTrip);

      }
      else{

         jobEngine.resumeJob(routineTrip);

      }

      return routineTripRepository.save(routineTrip);

   }


   @DeleteMapping(value = "/stop/{routineId}")
   public void deleteRoutine(String routineId) throws Exception {

      RoutineTrip routineTrip = routineTripRepository.findByRoutineId(UUID.fromString(routineId));

      jobEngine.deleteJob(routineTrip); 

   }


   @GetMapping(value = "/{routineId}")
   public RoutineTrip getRoutineTripByRoutineId(String routineId) {

      return routineTripRepository.findByRoutineId(UUID.fromString(routineId));

   }

   @GetMapping(value = "")
   public List<RoutineTrip> getAllRoutineTrip() {

      return routineTripRepository.findAll();

   }


}




*/
