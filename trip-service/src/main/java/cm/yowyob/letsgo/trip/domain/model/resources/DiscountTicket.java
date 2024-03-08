/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.resources;


import lombok.*;

import java.security.SecureRandom;
import java.util.HexFormat;


@Data
@Builder
@AllArgsConstructor
public class DiscountTicket {

    private final Float discountPercent;
    private final ResourceType concernType;
    private final String discountTicketId;
    private Float removeCost;
    private ResourceStatus status;


    public DiscountTicket(Float resourceCost, Float discountPercent, ResourceType concernType) throws IllegalArgumentException {

        if (concernType == ResourceType.DISCOUNT_TICKET)
            throw new IllegalArgumentException();

        if (discountPercent != null && resourceCost != null)
            throw new IllegalArgumentException();

        this.discountPercent = discountPercent;
        this.concernType = concernType;
        this.discountTicketId = generateTicketId();
        this.removeCost = resourceCost;
        this.status = ResourceStatus.FREE;
    }



    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @NonNull
    private static String generateTicketId(){

        byte[] bytes = new byte[4];
        SECURE_RANDOM.nextBytes(bytes);

        return HexFormat.of().formatHex(bytes).toUpperCase();
    }



}
