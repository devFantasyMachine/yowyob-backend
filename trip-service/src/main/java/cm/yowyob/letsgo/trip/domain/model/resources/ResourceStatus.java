/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.EnumSet;

public enum ResourceStatus {

    FREE(1F),
    RESERVED(0F),
    PARTIALLY_RESERVED(-0.5F),
    CONFIRMED(-1F),
    UNAVAILABLE(-2F);

    private final float quantity;

    ResourceStatus(float quantity) {
        this.quantity =quantity;
    }

    private static final EnumSet<ResourceStatus> ON_BUSY = EnumSet.of(RESERVED, CONFIRMED);
    private static final EnumSet<ResourceStatus> NO_BOOKABLE = EnumSet.of(RESERVED, CONFIRMED, UNAVAILABLE);

    public static ResourceStatus of(Float status) {

        if (status <= UNAVAILABLE.quantity)
                return UNAVAILABLE;

        if (status >= FREE.quantity)
            return FREE;

        if (status == RESERVED.quantity)
            return RESERVED;

        if (status == CONFIRMED.quantity)
            return CONFIRMED;

        if (status == PARTIALLY_RESERVED.quantity)
            return PARTIALLY_RESERVED;

        return null;
    }

    @JsonIgnore
    public boolean isBusy(){

        return ON_BUSY.contains(this);
    }

    @JsonIgnore
    public boolean isNonBookable(){

        return NO_BOOKABLE.contains(this);
    }

    @JsonIgnore
    public float getQuantity() {
        return quantity;
    }


}
