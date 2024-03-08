package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;

import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import lombok.NonNull;

import java.util.Collection;
import java.util.Objects;

public abstract class ItineraryState<L extends Leg> {

    protected final Itinerary<L> itinerary;

    public ItineraryState(Itinerary<L> itinerary) {
        this.itinerary = Objects.requireNonNull(itinerary);
    }


    public abstract void plan() throws ItineraryAlwaysPlannedException;
    public abstract void publish() throws IllegalItineraryPublicationException, ItineraryAlreadyPublishedException;
    public abstract void replaceLeg(L oldLeg, L newLeg) throws IllegalReplaceLegOperationException, LegNotFoundException;
    public abstract void removeLeg(@NonNull L otherLeg) throws LegNotFoundException, IllegalRemoveLegOperationException;
    public abstract void removeLegs(Collection<L> legs);
    public abstract void mergeLegs(Integer legIndex1, Integer legIndex2) throws IllegalMergeAttemptException;

}
