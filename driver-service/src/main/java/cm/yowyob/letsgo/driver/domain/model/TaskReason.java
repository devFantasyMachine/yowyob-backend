/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.driver.domain.model;




public enum TaskReason {

    OTHER("OTHER"),
    OWNER_PLAN("OWNER_PLAN"),
    RENTAL("RENTAL");

    private final String reason;

    TaskReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return reason;
    }
}
