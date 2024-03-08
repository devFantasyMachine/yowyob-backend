package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;

import cm.yowyob.letsgo.trip.domain.exceptions.*;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import lombok.NonNull;

import java.util.Collection;

public class ItineraryStatePublished<L extends Leg> extends ItineraryState<L> {

    public ItineraryStatePublished(Itinerary<L> itinerary) {
        super(itinerary);
    }

    @Override
    public void plan() throws ItineraryAlwaysPlannedException {

        throw new ItineraryAlwaysPlannedException();
    }

    @Override
    public void publish() throws ItineraryAlreadyPublishedException {

        throw new ItineraryAlreadyPublishedException();
    }



    @Override
    public void replaceLeg(L oldLeg, L newLeg) throws IllegalReplaceLegOperationException, LegNotFoundException {

        if (!this.itinerary.contains(oldLeg))
            throw new LegNotFoundException();

        if (oldLeg.isReserved()) {
            throw new IllegalReplaceLegOperationException();
        }

        if (newLeg.compareTo(oldLeg) != 0)
            throw new IllegalStateException();

        this.itinerary.legs.remove(oldLeg);
        this.itinerary.legs.add(newLeg);
        this.itinerary.resetDetails();
    }



    @Override
    public void removeLeg(final @NonNull L candidateLeg) throws LegNotFoundException, IllegalRemoveLegOperationException {

        if (!this.itinerary.contains(candidateLeg))
            throw new LegNotFoundException();

        if (candidateLeg.isReserved()) {
            throw new IllegalRemoveLegOperationException();
        }

        if (!this.itinerary.legs.remove(candidateLeg))
            this.itinerary.legs.removeIf(leg -> leg.getFromLocation().sameLocation(candidateLeg.getFromLocation())
                    && leg.getToLocation().sameLocation(candidateLeg.getToLocation()));

        this.itinerary.resetDetails();
    }


    @Override
    public void removeLegs(Collection<L> legs) {

    }

    @Override
    public void mergeLegs(Integer legIndex1, Integer legIndex2) throws IllegalMergeAttemptException {

    }
}
