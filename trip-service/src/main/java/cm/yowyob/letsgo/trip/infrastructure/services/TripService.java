/*

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.enspy.gi.project.trip_service.components.JobEngine;
import cm.enspy.gi.project.trip_service.models.*;
import cm.enspy.gi.project.trip_service.repositories.PlaceRepository;
import cm.enspy.gi.project.trip_service.repositories.PricingPolicyRepository;
import cm.enspy.gi.project.trip_service.repositories.ScheduledTripRepository;
import cm.enspy.gi.project.trip_service.repositories.TripRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    ScheduledTripRepository ScheduledTripRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PricingPolicyRepository pricingPolicyRepository;

    @Autowired
    JobEngine jobEngine;


    public List<Trip> getAllTrip() {

        return tripRepository.findAll();
    }

    public List<Trip> getAllTripByStatus(TripStatus status) {

        return tripRepository.findAllByStatus(status);
    }

    public Trip startTrip(String ScheduledId) throws Exception {

        log.info("START NEW TRIP BY " + ScheduledId);

        Optional<ScheduledTrip> optScheduledTrip = ScheduledTripRepository
                .findByScheduledId(UUID.fromString(ScheduledId));

        if (optScheduledTrip.isPresent()) {

            ScheduledTrip scheduledTrip = optScheduledTrip.get();

            Trip newTrip = Trip.simpleDraftBuilder().scheduledId(UUID.fromString(ScheduledId).toString())
                    .startDatetime(LocalDateTime.now())
                    .startLocation(scheduledTrip.getStartLocation())
                    .startCity(scheduledTrip.getStartCity())
                    .destinationCity(scheduledTrip.getDestinationCity())
                    .destinationLocation(scheduledTrip.getDestinationLocation())
                    .plannerId(scheduledTrip.getPlannerId())
                    .status(TripStatus.BEGUN)
                    .tripId(UUID.randomUUID())
                    .vehiculeId(scheduledTrip.getVehiculeId())
                    .driverId(scheduledTrip.getDriverId())
                    .comforts(scheduledTrip.getComforts())
                    .build();
            
            Trip savedTrip =  tripRepository.save(newTrip);
            scheduledTrip.setScheduledTripStatus(ScheduledTripStatus.TERMINATED);
            ScheduledTripRepository.save(scheduledTrip);

            List<Place> places = placeRepository.findAllByPlaceKeyTripId(scheduledTrip.getScheduledId());

            for (Place place : places) { 

                Set<PricingPolicy> policies = pricingPolicyRepository.findAllByPlaceId(place.getPlaceId());

                policies.stream().filter((policy) -> {

                    return policy.getJobStatus() != JobStatus.DELETED;

                }).map(jobEngine::deleteJob);

            }
            

            log.info("CREATE NEW TRIP SUCCEFULLY BY " + ScheduledId);

            return savedTrip;
            
        } else {

            log.info("CREATE NEW TRIP FAILED BY " + ScheduledId);

            throw new Exception();
        }
    }

   




    public Trip endTrip(String tripId) {

        Trip trip = tripRepository.findByTripId(UUID.fromString(tripId));

        trip.setEndDatetime(LocalDateTime.now());
        trip.setStatus(TripStatus.TERMINATED);

        return tripRepository.save(trip);

    }

    public Trip changeTripStatus(String tripId, TripStatus status) {

        Trip trip = tripRepository.findByTripId(UUID.fromString(tripId));
        trip.setStatus(status);

        return tripRepository.save(trip);

    }

    public Trip getTripById(String id) {

        return tripRepository.findByTripId(UUID.fromString(id));

    }

    public Boolean deleteTrip(String tripId) {

        tripRepository.deleteById(UUID.fromString(tripId));

        return Boolean.TRUE;

    }

    public Boolean addOnePassengerInTrip(String tripId, String ScheduledPlaceId, String userId) {

        // TODO 

        

        return Boolean.TRUE;

    }





}
*/
