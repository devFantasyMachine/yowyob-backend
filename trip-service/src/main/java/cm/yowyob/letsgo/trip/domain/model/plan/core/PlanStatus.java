package cm.yowyob.letsgo.trip.domain.model.plan.core;

import lombok.Getter;

@Getter
public enum PlanStatus {

    PLANNED("PLANNED"),


    /**
     * indicates if the planned trip is published
     */
    PUBLISHED("PUBLISHED"),


    /**
     * indicates whether the planned trip is totally booked for a planner trip plan,
     * or resolved for a pooler request plan
     */
    RESOLVED("RESOLVED"),


    /**
     * indicates if the planned trip is executed
     */
    EXECUTED("EXECUTED"),


    /**
     * indicates whether the published trip or the resolved trip is canceled
     */
    CANCELED("CANCELED");

    private final String status;

    PlanStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

}
