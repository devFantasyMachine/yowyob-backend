/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.resources;


import cm.yowyob.letsgo.trip.domain.model.policies.PricingPolicy;
import cm.yowyob.letsgo.trip.domain.utils.lang.StringUtils;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Data
@Builder
public class TripResource {

    private UUID id;
    private String label;
    private ResourceType type;
    private Float defaultUnitCost;
    private Float totalQuantity;
    private Float unitQuantity;
    private String unitTag;

    private PricingPolicy pricingPolicy;
    private Integer reservationCount;

    public TripResource(UUID id, String label, ResourceType type, Float defaultUnitCost, Float totalQuantity, Float unitQuantity, String unitTag, PricingPolicy pricingPolicy, Integer reservationCount) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.defaultUnitCost = defaultUnitCost;
        this.totalQuantity = totalQuantity;
        this.unitQuantity = unitQuantity;
        this.unitTag = unitTag;
        this.pricingPolicy = pricingPolicy;
        this.reservationCount = reservationCount;
    }

    public TripResource() {
    }


    public static List<TripResource> generateSeat(Integer seatCount, Float seatCost) {

        List<TripResource> resources = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < seatCount; i++) {
            indexList.add(i);
        }

        for (Integer index : indexList) {

            PlannedSeat plannedSeat =
                    new PlannedSeat(UUID.randomUUID(), formatNumber(seatCount, index), seatCost);

            resources.add(plannedSeat);
        }

        return resources;
    }


    public static String formatNumber(Integer count, Integer index) {

        return StringUtils.padLeft(index.toString(), '0', count.toString().length());
    }







}
