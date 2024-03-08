/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import cm.yowyob.letsgo.trip.domain.model.TripPrestige;
import cm.yowyob.letsgo.trip.domain.model.TripType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;



@Data
@Builder
public class PoolerRequestDTO {


    @Nonnull private LocalDateTime startDatetime;
    @Nonnull @NotBlank @NotEmpty private String plannerId;
    @Nonnull private PaymentSettingDTO paymentSetting;
    @Nonnull private StopLocationDTO fromLocation;
    @Nonnull private StopLocationDTO toLocation;
    @Nonnull private TripType tripType;

    private TripPrestige tripPrestige;
    private String note;
    private Set<String> comforts;
    private List<StopDTO> stopPoints;

    @Nullable private Integer placeCount;

    /**
     *  leg index : vehicle id
     */
    private final String vehicleId;
    private final Float luggageBoxQuantity;
    private final Float totalCost;

}
