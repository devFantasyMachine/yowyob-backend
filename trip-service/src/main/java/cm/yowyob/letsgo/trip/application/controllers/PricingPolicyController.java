/*
package cm.yowyob.letsgo.trip.application.controllers;

import cm.enspy.gi.project.trip_service.components.JobEngine;
import cm.enspy.gi.project.trip_service.models.Place;
import cm.enspy.gi.project.trip_service.models.PricingPolicy;
import cm.enspy.gi.project.trip_service.repositories.PlaceRepository;
import cm.enspy.gi.project.trip_service.repositories.PricingPolicyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v0/pricing-policies")
public class PricingPolicyController {

   @Autowired
   PlaceRepository placeRepository;

   @Autowired
   PricingPolicyRepository pricingPolicyRepository;

   @Autowired
   JobEngine jobEngine;

   @PostMapping(value = "/add-policy/{placeId}")
   public Place addPolicy(@PathVariable String placeId, @RequestBody PricingPolicy pricingPolicy) throws Exception {

      Place place = placeRepository.findByPlaceKeyPlaceId(UUID.fromString(placeId));

      if (place == null)
         throw new Exception();

      pricingPolicy.setAddAt(LocalDateTime.now());
      pricingPolicy.setPlaceId(UUID.fromString(placeId));
      pricingPolicy.setPolicyId(UUID.randomUUID());

      try {

         jobEngine.scheduleNewJob(pricingPolicy);

      } catch (Exception e) {

         log.error("Cannot schedule policy");
      } finally {

         pricingPolicyRepository.save(pricingPolicy);
      }

      place.setPolicies(pricingPolicyRepository.findAllByPlaceId(place.getPlaceId()));

      if (place.getPolicies().size() > 0)
         place.setPoliciesIsEnabled(true);

      return placeRepository.save(place);
   }

   @PutMapping(value = "/toggle/{policyId}")
   public PricingPolicy enableOrDisable(@PathVariable String policyId) throws Exception {

      PricingPolicy pricingPolicy = pricingPolicyRepository.findByPolicyId(UUID.fromString(policyId));

      if (pricingPolicy.getIsEnabled()) {

         jobEngine.pauseJob(pricingPolicy);

      } else {

         jobEngine.resumeJob(pricingPolicy);

      }

      return pricingPolicyRepository.save(pricingPolicy);

   }

   @DeleteMapping(value = "/remove/{policyId}")
   public void removePricingPolicy(@PathVariable String policyId) throws Exception {

      PricingPolicy policy = pricingPolicyRepository.findByPolicyId(UUID.fromString(policyId));
      jobEngine.deleteJob(policy);

      if (policy.getRepeatCount() == 0) {
         pricingPolicyRepository.delete(policy);
      } else {
         pricingPolicyRepository.save(policy);
      }
      
      Place place = placeRepository.findByPlaceKeyPlaceId(policy.getPlaceId());
      place.setPolicies(pricingPolicyRepository.findAllByPlaceId(place.getPlaceId()));
      
      if (place.getPolicies().size() == 0){
      
         place.setPoliciesIsEnabled(false);

         placeRepository.save(place);
      
      }
 
   }

   @GetMapping(value = "/{placeId}")
   public Set<PricingPolicy> getAllPricingPolicyByPlaceId(@PathVariable String placeId) {

      return pricingPolicyRepository.findAllByPlaceId(UUID.fromString(placeId));

   }

}
*/
