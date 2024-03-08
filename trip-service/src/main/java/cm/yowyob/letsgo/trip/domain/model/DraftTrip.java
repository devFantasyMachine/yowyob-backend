/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;

import cm.yowyob.letsgo.trip.domain.model.plan.ServiceType;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import cm.yowyob.letsgo.trip.domain.model.vehicle.Comfort;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;


@Data
@Builder
public class DraftTrip {

    private UUID draftId;
    private String ownerId;
    private StopLocation fromLocation;
    private StopLocation toLocation;
    private List<Stop> intermediateStops;
    private TripType tripType;
    private ServiceType serviceType;
    private TripPrestige tripPrestige;
    private Set<Comfort> comforts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private Integer usageCount = 0;


    public DraftTrip(UUID draftId, String ownerId, StopLocation fromLocation, StopLocation toLocation, List<Stop> intermediateStops, TripType tripType, ServiceType serviceType, TripPrestige tripPrestige, Set<Comfort> comforts, LocalDateTime createdAt, LocalDateTime updatedAt, Integer usageCount) {
        this.draftId = Objects.requireNonNullElse(draftId, UUID.randomUUID());
        this.ownerId = ownerId;
        this.fromLocation = Objects.requireNonNull(fromLocation);
        this.toLocation = Objects.requireNonNull(toLocation);
        this.tripType = Objects.requireNonNull(tripType);
        this.serviceType = serviceType;
        this.tripPrestige = Objects.requireNonNullElse(tripPrestige, TripPrestige.NORMAL);
        this.comforts = Objects.requireNonNullElse(comforts, new HashSet<>());
        this.createdAt = Objects.requireNonNullElse(createdAt, LocalDateTime.now());
        this.intermediateStops = intermediateStops == null ? new ArrayList<>() : intermediateStops;
        this.updatedAt = Objects.requireNonNullElse(updatedAt, LocalDateTime.now());
        this.usageCount = Objects.requireNonNullElse(usageCount, 0);
    }




    public static DraftTripBuilder simpleDraftBuilder() {
        return new DraftTripBuilder();
    }

    public void increaseUsageCount() {
        this.usageCount = Objects.requireNonNullElse(usageCount, 0) + 1;
    }


    public static class DraftTripBuilder {
        private UUID draftId;
        private String ownerId;
        private StopLocation fromLocation;
        private StopLocation toLocation;
        private List<Stop> intermediateStops;
        private TripType tripType;
        private TripPrestige tripPrestige;
        private Set<Comfort> comforts = new HashSet<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private Integer usageCount = 0;

        private ServiceType serviceType = ServiceType.DAY;

        DraftTripBuilder() {
        }

        public DraftTripBuilder draftId(UUID draftId) {
            this.draftId = draftId;
            return this;
        }

        public DraftTripBuilder ownerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public DraftTripBuilder serviceType(ServiceType serviceType) {
            this.serviceType = serviceType;
            return this;
        }

        public DraftTripBuilder usageCount(Integer usageCount) {
            this.usageCount = usageCount;
            return this;
        }

        public DraftTripBuilder fromLocation(StopLocation fromLocation) {
            this.fromLocation = fromLocation;
            return this;
        }

        public DraftTripBuilder toLocation(StopLocation toLocation) {
            this.toLocation = toLocation;
            return this;
        }

        public DraftTripBuilder intermediateStops(List<Stop> intermediateStops) {
            this.intermediateStops = intermediateStops;
            return this;
        }

        public DraftTripBuilder tripType(TripType tripType) {
            this.tripType = tripType;
            return this;
        }

        public DraftTripBuilder tripPrestige(TripPrestige tripPrestige) {
            this.tripPrestige = tripPrestige;
            return this;
        }

        public DraftTripBuilder comforts(Set<Comfort> comforts) {
            this.comforts = comforts;
            return this;
        }

        public DraftTripBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DraftTripBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public DraftTrip build() {
            return new DraftTrip(this.draftId, this.ownerId, this.fromLocation, this.toLocation, this.intermediateStops, this.tripType, serviceType, this.tripPrestige, this.comforts, this.createdAt, this.updatedAt, usageCount);
        }

        public String toString() {
            return "DraftTrip.DraftTripBuilder(draftId=" + this.draftId + ", ownerId=" + this.ownerId + ", fromLocation=" + this.fromLocation + ", toLocation=" + this.toLocation + ", intermediateStops=" + this.intermediateStops + ", tripType=" + this.tripType + ", tripPrestige=" + this.tripPrestige + ", comforts=" + this.comforts + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }

    }


}
