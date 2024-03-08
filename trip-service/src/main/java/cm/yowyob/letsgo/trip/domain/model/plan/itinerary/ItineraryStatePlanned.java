package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;

import cm.yowyob.letsgo.trip.domain.exceptions.IllegalMergeAttemptException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalReplaceLegOperationException;
import cm.yowyob.letsgo.trip.domain.exceptions.ItineraryAlwaysPlannedException;
import cm.yowyob.letsgo.trip.domain.exceptions.LegNotFoundException;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import lombok.NonNull;

import java.util.Collection;

public class ItineraryStatePlanned<L extends Leg> extends ItineraryState<L> {

    public ItineraryStatePlanned(Itinerary<L> itinerary) {
        super(itinerary);
    }

    @Override
    public void plan() throws ItineraryAlwaysPlannedException {

        throw new ItineraryAlwaysPlannedException();
    }

    @Override
    public void publish() {

        this.itinerary.state = new ItineraryStatePublished<>(this.itinerary);
    }


    @Override
    public void replaceLeg(L oldLeg, L newLeg) throws IllegalReplaceLegOperationException, LegNotFoundException {

    }

    @Override
    public void removeLeg(@NonNull L otherLeg) {

    }

    @Override
    public void removeLegs(Collection<L> legs) {

    }

    @Override
    public void mergeLegs(Integer legIndex1, Integer legIndex2) throws IllegalMergeAttemptException {

    }


}
