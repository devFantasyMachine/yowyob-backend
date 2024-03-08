/*

package cm.yowyob.letsgo.trip.infrastructure.services;

import cm.enspy.gi.project.trip_service.components.JobEngine;
import cm.enspy.gi.project.trip_service.models.*;
import cm.enspy.gi.project.trip_service.repositories.PlaceRepository;
import cm.enspy.gi.project.trip_service.repositories.PricingPolicyRepository;
import cm.enspy.gi.project.trip_service.repositories.ScheduledTripRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ScheduledTripService {

    @Autowired
    NotificationService notificationService;

    @Autowired
    PreferencesService preferencesService;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PricingPolicyRepository pricingPolicyRepository;

    @Autowired
    JobEngine pricingEngine;

    @Autowired
    ScheduledTripRepository ScheduledTripRepository;


    
    public ScheduledTrip createScheduledTrip(ScheduledTrip trip) throws Exception {

        if(trip.getPlannerType() == null)throw new IllegalArgumentException("");

        trip.setScheduledId(UUID.randomUUID());
        trip.setPostAt(LocalDateTime.now()); 
        trip.setTravelerDetails(null);
        trip.setPlannerDetails(null);
        trip.setScheduledTripStatus(ScheduledTripStatus.CREATED); 

        trip = ScheduledTripRepository.save(trip);

        try {

            notificationService.sendNewScheduledTrip(trip);

        } catch (Exception e) {

            log.error("error when sending notification");
        }

        return trip;
    }

    
    public ScheduledTrip createScheduledTripForPlanner(ScheduledTrip trip) throws Exception {

        if (trip.getDriverId() == null || trip.getVehiculeId() == null )
            throw new IllegalArgumentException("driverId and vehiculeId must not be null");


        trip.setScheduledId(UUID.randomUUID());
        trip.setPostAt(LocalDateTime.now());
        trip.setPlaceCount(0);
        trip.setScheduledTripStatus(ScheduledTripStatus.CREATED); 


        Map<Integer, String> infoPlaces = preferencesService.getPlacesInfo(trip.getPlannerId().toString(),
                trip.getVehiculeId().toString());
        List<Place> places = new ArrayList<>();

        for (Integer number : infoPlaces.keySet()) {

            Place newPlace = Place.simpleDraftBuilder()
                    .addAt(LocalDateTime.now())
                    .placeNumber(number) 
                    .status(PlaceStatus.UNAVAILABLE)
                    .initialStatus(PlaceStatus.UNAVAILABLE)
                    .policiesIsEnabled(false)                    
                    .initialCost(0l)
                    .placeCost(0l)
                    .policies(null)
                    .placeKey(new PlaceKey(trip.getScheduledId(), UUID.randomUUID()))
                    .build();

            newPlace = placeRepository.save(newPlace);
            places.add(newPlace);
        }

        trip = ScheduledTripRepository.save(trip);
        trip.setPlaces(places);

        try {

            notificationService.sendNewScheduledTrip(trip);

        } catch (Exception e) {

            log.error("error when sending notification");
        }

        return trip;
    }

    public ScheduledTrip enableOrDisableScheduledTrip(String tripId, boolean enable) throws Exception {

        ScheduledTrip trip = ScheduledTripRepository.findByScheduledId(UUID.fromString(tripId)).get();

        if (!(trip.getScheduledTripStatus() == ScheduledTripStatus.ENABLED || trip.getScheduledTripStatus() == ScheduledTripStatus.ACTIVATE || trip.getScheduledTripStatus() == ScheduledTripStatus.DISABLED ))
            throw new IllegalArgumentException("status must be enabled or disabled before performing task");


        List<Place> places = placeRepository.findAllByPlaceKeyTripId(trip.getScheduledId());

        Long count = places.stream().filter((place) -> {

            return place.getJobStatus() == PlaceStatus.BUSY || place.getJobStatus() == PlaceStatus.RESERVED;

        }).count();

        if (count != 0l)
            throw new Exception("One or more place are already busy");

        if ((trip.getScheduledTripStatus() == ScheduledTripStatus.ENABLED && enable) || (trip.getScheduledTripStatus() == ScheduledTripStatus.DISABLED && !enable)) 
        return trip;

        for (Place place : places) {

            if (place.isPoliciesIsEnabled()) {

                Set<PricingPolicy> policies = pricingPolicyRepository.findAllByPlaceId(place.getPlaceId());

                policies.stream().map(enable ? pricingEngine::resumeJob  : pricingEngine::pauseJob );
                pricingPolicyRepository.saveAll(policies);
                place.setPolicies(policies);                 
            }            
        }

        trip.setScheduledTripStatus(enable ? ScheduledTripStatus.ENABLED : ScheduledTripStatus.DISABLED); 
        trip = ScheduledTripRepository.save(trip);
        trip.setPlaces(places);

        return trip;
    }
    
    
    public ScheduledTrip setUpScheduledTripForTraveler(String tripId, boolean enable, TravelerDetails request) throws Exception {


        ScheduledTrip trip = ScheduledTripRepository.findByScheduledId(UUID.fromString(tripId)).get();

        if (!(trip.getScheduledTripStatus() == ScheduledTripStatus.ACTIVATE || trip.getScheduledTripStatus() == ScheduledTripStatus.CREATED))
            throw new IllegalCallerException("scheduled trip is already enabled");
 
 
        trip.setScheduledTripStatus(enable ? ScheduledTripStatus.ENABLED : ScheduledTripStatus.ACTIVATE ) ; 
        trip.setTravelerDetails(request);
        trip.setPlaceCount(request.getPlaceCount());

        trip = ScheduledTripRepository.save(trip); 

        return trip;
    }





    public ScheduledTrip setUpScheduledTripForPlanner(String tripId, boolean enable,
            SetupScheduledTripRequest request) throws Exception {


        ScheduledTrip trip = ScheduledTripRepository.findByScheduledId(UUID.fromString(tripId)).get();

        if (!(trip.getScheduledTripStatus() == ScheduledTripStatus.ACTIVATE || trip.getScheduledTripStatus() == ScheduledTripStatus.CREATED))
            throw new IllegalCallerException("scheduled trip is already enabled");


        // si activate on supprime les anciennes places

        List<Place> places = placeRepository.findAllByPlaceKeyTripId(trip.getScheduledId());

        for (Place place : places) {

            Set<PricingPolicy> oldPolicies = pricingPolicyRepository.findAllByPlaceId(place.getPlaceId());
            pricingPolicyRepository.deleteAll(oldPolicies); 
            placeRepository.delete(place);   
        }

        for (Integer number : request.getPlacesRequest().keySet()) {

            SetupPlaceRequest placeRequest = request.getPlacesRequest().get(number); 

            Place newPlace = Place.simpleDraftBuilder()
                    .addAt(LocalDateTime.now())
                    .placeNumber(number) 
                    .status(placeRequest.getJobStatus())
                    .initialStatus(placeRequest.getJobStatus())
                    .policiesIsEnabled(false)                    
                    .initialCost(request.getPlannerDetails().getStandardCost())
                    .placeCost(request.getPlannerDetails().getStandardCost())
                    .policies(null)
                    .placeKey(new PlaceKey(trip.getScheduledId(), UUID.randomUUID()))
                    .build();

            newPlace = placeRepository.save(newPlace); 


        if(newPlace.getJobStatus() == PlaceStatus.FREE)trip.setPlaceCount(trip.getPlaceCount() + 1);

        Set<PricingPolicy> policies = placeRequest.getPolicies();

        if (policies == null || policies.size() == 0) {

            newPlace.setPoliciesIsEnabled(false);

        } else {

            newPlace.setPoliciesIsEnabled(true);
            newPlace.setStatus(PlaceStatus.FREE);

            final UUID placeId = newPlace.getPlaceId();

            policies.forEach((policy) -> {

                policy.setPolicyId(UUID.randomUUID());
                policy.setPlaceId(placeId);
                policy.setAddAt(LocalDateTime.now());
                policy.setJobStatus(JobStatus.UNSCHEDULED);

                try {

                    pricingEngine.scheduleNewJob(policy);
                    if(!enable)pricingEngine.pauseJob(policy);

                } catch (Exception e) {

                    log.error("We cannot schedule policy", e);

                } finally {

                    pricingPolicyRepository.save(policy);
                }

            });
        }


        placeRepository.save(newPlace);

    }

        trip.setScheduledTripStatus(enable ? ScheduledTripStatus.ENABLED : ScheduledTripStatus.ACTIVATE ) ; 
        trip.setPlannerDetails(request.getPlannerDetails());

        trip = ScheduledTripRepository.save(trip);
        trip.setPlaces(places); 

        return trip;
    }

    public ScheduledTrip getScheduledTripInfos(String tripId) throws Exception {

        ScheduledTrip trip = ScheduledTripRepository.findByScheduledId(UUID.fromString(tripId)).get();

        List<Place> places = placeRepository.findAllByPlaceKeyTripId(trip.getScheduledId());

        for (Place place : places) {

            place.setPolicies(pricingPolicyRepository.findAllByPlaceId(place.getPlaceId()));

        }

        trip.setPlaces(places);

        return trip;
    }

    public ScheduledTrip createScheduledTripByAnother(String tripId) {

        ScheduledTrip trip = ScheduledTripRepository.findByScheduledId(UUID.fromString(tripId)).get();
        List<Place> places = placeRepository.findAllByPlaceKeyTripId(trip.getScheduledId());
        List<Place> placesResult = new ArrayList<>();

        trip.setScheduledId(UUID.randomUUID());
        trip.setPostAt(LocalDateTime.now());
        trip.setScheduledTripStatus(ScheduledTripStatus.ACTIVATE);

        for (Place place : places) {

            Place newPlace = place.resetPlace(trip.getScheduledId());
            newPlace = placeRepository.save(newPlace);            

            if (place.isPoliciesIsEnabled()) {

                newPlace.setPoliciesIsEnabled(true);
                final UUID placeId = newPlace.getPlaceId();

                Set<PricingPolicy> policies = pricingPolicyRepository.findAllByPlaceId(place.getPlaceId());

                List<PricingPolicy> newPolicies = policies.stream().map((policy)-> {
                     
                    return PricingPolicy.resetPolicy(policy, placeId);

                }).toList();
                newPolicies = pricingPolicyRepository.saveAll(newPolicies);  
                
                try {

                    newPolicies.stream().map(pricingEngine::scheduleNewJob);
                    newPolicies.stream().map(pricingEngine::pauseJob);
                    
                } catch (Exception e) {
                    log.error(e.getMessage());
                }                

            } 
            
            placesResult.add(newPlace);
        }

        trip = ScheduledTripRepository.save(trip);
        trip.setPlaces(placesResult);

        return trip;
    }

    public ScheduledTrip changeDriver(String tripId, String driverId) throws Exception {

        ScheduledTrip trip = ScheduledTripRepository.findByScheduledId(UUID.fromString(tripId)).get();
        trip.setDriverId(UUID.fromString(driverId));

        return ScheduledTripRepository.save(trip);

    }

    public ScheduledTrip changeVehicule(String tripId, String vehiculeId) throws Exception {

        ScheduledTrip trip = ScheduledTripRepository.findByScheduledId(UUID.fromString(tripId)).get();

        List<Place> oldPlaces = placeRepository.findAllByPlaceKeyTripId(trip.getScheduledId());

        Long count = oldPlaces.stream().filter((place) -> {

            return place.getJobStatus() == PlaceStatus.BUSY || place.getJobStatus() == PlaceStatus.RESERVED;

        }).count();

        if (count != 0l)
            throw new Exception();

        // on supprime les policies job

        placeRepository.deleteAll(oldPlaces);
        trip.setPlaceCount(0);
        trip.setScheduledTripStatus(ScheduledTripStatus.CREATED); 

        trip = ScheduledTripRepository.save(trip); 

        try {

            notificationService.sendVehiculeForScheduledTrip(trip);

        } catch (Exception e) {

            log.error("error when sending notification");
        }

        return trip;

    }


    public ScheduledTrip terminateScheduledTrip(String id) {

        ScheduledTrip trip =  ScheduledTripRepository.findByScheduledId(UUID.fromString(id)).get();

        trip.setScheduledTripStatus(ScheduledTripStatus.TERMINATED);

        return ScheduledTripRepository.save(trip);
    }

    public ScheduledTrip deleteScheduledTrip(String id) {

        ScheduledTrip trip =  ScheduledTripRepository.findByScheduledId(UUID.fromString(id)).get();

        trip.setScheduledTripStatus(ScheduledTripStatus.DELETED);

        return ScheduledTripRepository.save(trip);
    }

    public ScheduledTrip getTripByScheduledId(String id) {

        return ScheduledTripRepository.findByScheduledId(UUID.fromString(id)).get();

    }

    public List<ScheduledTrip> getAllScheduledTrip() {

        return ScheduledTripRepository.findAll();

    }

}
*/
