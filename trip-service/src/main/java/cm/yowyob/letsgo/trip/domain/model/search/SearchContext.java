/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.search;


import cm.yowyob.letsgo.trip.domain.model.plan.stops.StopLocation;
import lombok.NonNull;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;



public record SearchContext(ZonedDateTime departure,
                            StopLocation fromLocation,
                            StopLocation toLocation,
                            Integer placeCount,
                            Set<StopLocation> intermediateStops) {


    public boolean hasIntermediateStops() {
        return intermediateStops != null && !intermediateStops.isEmpty();
    }


    public @NonNull static SearchContextBuilder builder() {
        return new SearchContextBuilder();
    }



    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof final SearchContext other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$departure = this.departure();
        final Object other$departure = other.departure();
        if (!Objects.equals(this$departure, other$departure)) return false;
        final Object this$fromLocation = this.fromLocation();
        final Object other$fromLocation = other.fromLocation();
        if (!Objects.equals(this$fromLocation, other$fromLocation))
            return false;
        final Object this$toLocation = this.toLocation();
        final Object other$toLocation = other.toLocation();
        if (!Objects.equals(this$toLocation, other$toLocation))
            return false;
        final Object this$placeCount = this.placeCount();
        final Object other$placeCount = other.placeCount();
        if (!Objects.equals(this$placeCount, other$placeCount))
            return false;
        final Object this$intermediateStops = this.intermediateStops();
        final Object other$intermediateStops = other.intermediateStops();

        return Objects.equals(this$intermediateStops, other$intermediateStops);
    }

    private boolean canEqual(final Object other) {
        return other instanceof SearchContext;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $departure = this.departure();
        result = result * PRIME + ($departure == null ? 43 : $departure.hashCode());
        final Object $fromLocation = this.fromLocation();
        result = result * PRIME + ($fromLocation == null ? 43 : $fromLocation.hashCode());
        final Object $toLocation = this.toLocation();
        result = result * PRIME + ($toLocation == null ? 43 : $toLocation.hashCode());
        final Object $placeCount = this.placeCount();
        result = result * PRIME + ($placeCount == null ? 43 : $placeCount.hashCode());
        final Object $intermediateStops = this.intermediateStops();
        result = result * PRIME + ($intermediateStops == null ? 43 : $intermediateStops.hashCode());
        return result;
    }

    public String toString() {
        return "SearchContext(departure=" + this.departure() + ", fromLocation=" + this.fromLocation() + ", toLocation=" + this.toLocation() + ", placeCount=" + this.placeCount() + ", intermediateStops=" + this.intermediateStops() + ")";
    }

    public static class SearchContextBuilder {
        private ZonedDateTime departure;
        private StopLocation fromLocation;
        private StopLocation toLocation;
        private Integer placeCount;
        private Set<StopLocation> intermediateStops;

        SearchContextBuilder() {
        }

        public SearchContextBuilder departure(ZonedDateTime departure) {
            this.departure = departure;
            return this;
        }

        public SearchContextBuilder fromLocation(StopLocation fromLocation) {
            this.fromLocation = fromLocation;
            return this;
        }

        public SearchContextBuilder toLocation(StopLocation toLocation) {
            this.toLocation = toLocation;
            return this;
        }

        public SearchContextBuilder placeCount(Integer placeCount) {
            this.placeCount = placeCount;
            return this;
        }

        public SearchContextBuilder intermediateStops(Set<StopLocation> intermediateStops) {
            this.intermediateStops = intermediateStops;
            return this;
        }

        public SearchContext build() {
            return new SearchContext(departure, fromLocation, toLocation, placeCount, intermediateStops);
        }

        public String toString() {
            return "SearchContext.SearchContextBuilder(departure=" + this.departure + ", fromLocation=" + this.fromLocation + ", toLocation=" + this.toLocation + ", placeCount=" + this.placeCount + ", intermediateStops=" + this.intermediateStops + ")";
        }
    }
}
