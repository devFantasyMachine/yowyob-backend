/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;



@Data
@Builder
@AllArgsConstructor
public final class Driver {

    public static final Driver NONE = Driver.builder().build();

    private final Score score;
    private final String ownerId;
    private final String driverId;
    private final String firstName;
    private final String lastName;
    private final String avatar;
    private final String picture;
    private final String gender;
    private final String about;
    private final Integer version;

}

