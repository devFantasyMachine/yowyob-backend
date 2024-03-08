package cm.yowyob.letsgo.trip.domain.model.plan.itinerary;

import cm.yowyob.letsgo.trip.domain.exceptions.IllegalItineraryPublicationException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalMergeAttemptException;
import cm.yowyob.letsgo.trip.domain.exceptions.IllegalReplaceLegOperationException;
import cm.yowyob.letsgo.trip.domain.exceptions.LegNotFoundException;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ItineraryStateDrafted<L extends Leg> extends ItineraryState<L> {

    public ItineraryStateDrafted(Itinerary<L> itinerary) {
        super(itinerary);
    }

    @Override
    public void plan() {

        this.itinerary.state = new ItineraryStatePlanned<>(this.itinerary);

    }

    @Override
    public void publish() throws IllegalItineraryPublicationException {

        throw new IllegalItineraryPublicationException();
    }

    @Override
    public void replaceLeg(L oldLeg, L newLeg) throws IllegalReplaceLegOperationException, LegNotFoundException {

        if (!this.itinerary.contains(oldLeg))
            throw new LegNotFoundException();

        if (newLeg.compareTo(oldLeg) != 0)
            throw new IllegalReplaceLegOperationException();

        this.itinerary.legs.remove(oldLeg);
        this.itinerary.legs.add(newLeg);
        this.itinerary.resetDetails();
    }

    @Override
    public void removeLeg(@NonNull L otherLeg) {

        if (!this.itinerary.legs.remove(otherLeg))
            this.itinerary.legs.removeIf(leg -> leg.getFromLocation().sameLocation(otherLeg.getFromLocation())
                    && leg.getToLocation().sameLocation(otherLeg.getToLocation()));

        this.itinerary.resetDetails();

    }

    @Override
    public void removeLegs(Collection<L> legs) {

        this.itinerary.legs.removeAll(legs);
        this.itinerary.resetDetails();

    }

    @Override
    public void mergeLegs(Integer legIndex1, Integer legIndex2) throws IllegalMergeAttemptException {

        Integer lastIndex = legIndex2 == null ? this.itinerary.length() -1 : legIndex2;

        if (lastIndex <= legIndex1 || this.itinerary.length() <= lastIndex )
            throw new IllegalArgumentException();

        for (int i = legIndex1; i < lastIndex ; i++) {

            mergeConsecutivelyLegs(i, i+1);
        }

    }

    @SuppressWarnings("unchecked")
    private void mergeConsecutivelyLegs(final Integer legIndex1, final Integer legIndex2) throws IllegalMergeAttemptException {

        if (legIndex2 != legIndex1 + 1)
            throw new IllegalMergeAttemptException();

        L leg1 = this.itinerary.getLeg(legIndex1);
        L leg2 = this.itinerary.getLeg(legIndex2);

        if (leg1 == null || leg2 == null)
            throw new IllegalMergeAttemptException();

        L leg = (L) leg1.merge(leg2);

        removeLeg(leg1);

        Set<L> newLegs = new HashSet<>();

        for (L l : this.itinerary.legs.tailSet(leg2, true)) {

            L newLeg = (L) l.withPositionShift(-1);
            newLegs.add(newLeg);
        }

        removeLegs(this.itinerary.legs.tailSet(leg2));

        this.itinerary.addLegs(newLegs);
        this.itinerary.addLeg(leg);
    }




}
