/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.schedule;

public enum JobStatus {

    SCHEDULED("SCHEDULED"), UNSCHEDULED("UNSCHEDULED"), EDITED_SCHEDULED("EDITED & SCHEDULED"), PAUSED("PAUSED"),
    SCHEDULED_STARTED("SCHEDULED & STARTED"), RESUMED("RESUMED") , DELETED("DELETED");

    private final String value;

    JobStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }


}
