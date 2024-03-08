/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.policies;


import java.time.Duration;


/**
 *  durationBeforeLastReservation is duration between posted datetime and last reservation datetime
 */
public record ReservationPolicy (Boolean confirmWithReservation,
                                 Boolean requirePlannerConsent,
                                 Duration durationBeforeLastReservation,
                                 Duration confirmationDelayAfterReservation,
                                 Float maxLuggagePerUser,
                                 Float maxFreeLuggageWeightPerUser,
                                 Integer maxPlaceCountPerUser) {



    public static final ReservationPolicy DEFAULT =

             new ReservationPolicy(true,
                     true,
                     Duration.ofDays(30),
                     Duration.ofMinutes(60),
                     Float.MAX_VALUE,
                     Float.MAX_VALUE,
                     Integer.MAX_VALUE);



    public static ReservationPolicyBuilder builder() {
        return new ReservationPolicyBuilder();
    }



    public static class ReservationPolicyBuilder {
        private Boolean confirmWithReservation;
        private Boolean requirePlannerConsent;
        private Duration durationBeforeLastReservation;
        private Duration confirmationDelayAfterReservation;
        private Integer maxPlaceCountPerUser = Integer.MAX_VALUE;
        private Float maxLuggagePerUser = Float.MAX_VALUE;

        private Float maxFreeLuggageWeightPerUser;

        ReservationPolicyBuilder() {
        }

        public ReservationPolicyBuilder maxFreeLuggageWeightPerUser(Float maxFreeLuggageWeightPerUser) {
            this.maxFreeLuggageWeightPerUser = maxFreeLuggageWeightPerUser;
            return this;
        }

        public ReservationPolicyBuilder maxLuggageForUser(Float maxLuggageForUser) {
            this.maxLuggagePerUser = maxLuggageForUser;
            return this;
        }

        public ReservationPolicyBuilder confirmWithReservation(Boolean confirmWithReservation) {
            this.confirmWithReservation = confirmWithReservation;
            return this;
        }

        public ReservationPolicyBuilder maxPlaceCountPerUser(Integer maxPlaceCountPerUser) {
            this.maxPlaceCountPerUser = maxPlaceCountPerUser;
            return this;
        }

        public ReservationPolicyBuilder durationBeforeLastReservation(Duration durationBeforeLastReservation) {
            this.durationBeforeLastReservation = durationBeforeLastReservation;
            return this;
        }

        public ReservationPolicyBuilder confirmationDelayAfterReservation(Duration confirmationDelayAfterReservation) {
            this.confirmationDelayAfterReservation = confirmationDelayAfterReservation;
            return this;
        }

        public ReservationPolicy build() {
            return new ReservationPolicy(confirmWithReservation, requirePlannerConsent, durationBeforeLastReservation, confirmationDelayAfterReservation, maxLuggagePerUser,maxFreeLuggageWeightPerUser, maxPlaceCountPerUser);
        }

        public String toString() {
            return "ReservationPolicy.ReservationPolicyBuilder(confirmWithReservation=" + this.confirmWithReservation + ", durationBeforeLastReservation=" + this.durationBeforeLastReservation + ", confirmationDelayAfterReservation=" + this.confirmationDelayAfterReservation + ")";
        }

    }

}
