/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.application.dto;


import jakarta.annotation.Nonnull;
import jakarta.validation.UnexpectedTypeException;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class RangeDateDTO {

    ZonedDateTime from;

    @Nonnull
    ZonedDateTime to;

    public RangeDateDTO(ZonedDateTime from, @Nonnull ZonedDateTime to) {

        if (from == null){

            this.from = ZonedDateTime.of(23, 1, 1, 0, 0, 0, 0, to.getZone());
        }
        else {

            if (from.compareTo(to) > 0)
                throw new UnexpectedTypeException();

            this.from = from;
        }

        this.to = to;
    }
}
