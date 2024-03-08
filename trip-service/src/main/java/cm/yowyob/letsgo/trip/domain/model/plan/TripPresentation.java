/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.plan;

import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;
import java.util.Set;



public record TripPresentation(String note,
                               Set<TripPresentationItem> tripPresentationItems) {


    public static final TripPresentation NONE = new TripPresentation("", Set.of());



    @Data
    @AllArgsConstructor
    public static class TripPresentationItem {

        private final String text;
        private final Set<String> images;
    }


    @Getter
    public static class ScheduledTripPresentationItem extends TripPresentationItem {

        private final StopLocation stopLocation;

        public ScheduledTripPresentationItem(TripPresentationItem item, StopLocation stopLocation) {
            super(Objects.requireNonNull(item).text, item.images);
            this.stopLocation = Objects.requireNonNull(stopLocation);
        }
    }


    public static TripPresentationBuilder builder() {
        return new TripPresentationBuilder();
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof final TripPresentation other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$note = this.note();
        final Object other$note = other.note();
        if (!Objects.equals(this$note, other$note)) return false;
        final Object this$tripPresentationItems = this.tripPresentationItems();
        final Object other$tripPresentationItems = other.tripPresentationItems();
        return Objects.equals(this$tripPresentationItems, other$tripPresentationItems);
    }

    private boolean canEqual(final Object other) {
        return other instanceof TripPresentation;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $note = this.note();
        result = result * PRIME + ($note == null ? 43 : $note.hashCode());
        final Object $tripPresentationItems = this.tripPresentationItems();
        result = result * PRIME + ($tripPresentationItems == null ? 43 : $tripPresentationItems.hashCode());
        return result;
    }

    public @NonNull String toString() {
        return "TripPresentation(note=" + this.note() + ", tripPresentationItems=" + this.tripPresentationItems() + ")";
    }

    public static class TripPresentationBuilder {
        private String note;
        private Set<TripPresentationItem> tripPresentationItems;

        TripPresentationBuilder() {
        }

        public TripPresentationBuilder note(String note) {
            this.note = note;
            return this;
        }

        public TripPresentationBuilder tripPresentationItems(Set<TripPresentationItem> tripPresentationItems) {
            this.tripPresentationItems = tripPresentationItems;
            return this;
        }

        public TripPresentation build() {
            return new TripPresentation(note, tripPresentationItems);
        }

        public String toString() {
            return "TripPresentation.TripPresentationBuilder(note=" + this.note + ", tripPresentationItems=" + this.tripPresentationItems + ")";
        }
    }
}
