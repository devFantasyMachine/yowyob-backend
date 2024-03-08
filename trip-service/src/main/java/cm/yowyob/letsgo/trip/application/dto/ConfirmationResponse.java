/*
 * Copyright (c) 2023-2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


@Data
@Builder
public class ConfirmationResponse {

    private static final String TIMEOUT_CODE = "TIMEOUT";
    private static final String UNAUTHORIZED_CODE = "UNAUTHORIZED";


    private boolean isSuccess;
    private String detailCode;

    @NonNull
    public static ConfirmationResponse failConfirmationResponse(String detailCode){

        return new ConfirmationResponse(false, detailCode);
    }

    @NonNull
    public static ConfirmationResponse successConfirmationResponse(String detailCode){

        return new ConfirmationResponse(true, detailCode);
    }

}
