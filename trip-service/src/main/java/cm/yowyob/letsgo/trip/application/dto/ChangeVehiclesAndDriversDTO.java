/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import jakarta.annotation.Nonnull;
import lombok.Data;

import java.util.Map;


@Data
public class ChangeVehiclesAndDriversDTO {

    @Nonnull private String tripPlanId;
    private Map<Integer, String> vehicleMapIds;
    private Map<Integer, String> driverMapIds;
}
