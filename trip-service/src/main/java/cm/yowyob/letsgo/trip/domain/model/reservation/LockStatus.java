/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.reservation;

import java.util.EnumSet;

public enum LockStatus {

    LOCKED,

    CONFIRMED,

    CANCELED,

    /**
     *  Aborted because timeout
     */
    ABORTED;

    private static final EnumSet<LockStatus>  NON_CONFIRMABLE = EnumSet.of(CANCELED, ABORTED);

    public boolean isNonConfirmable() {

        return NON_CONFIRMABLE.contains(this);
    }


}
