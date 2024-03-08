/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.domain.model.vehicle;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Seat {

    private final String seatLabel;
    private final Integer seatNumber;
    private final Set<Comfort> comforts;

    public Seat(Integer placeNumber, String placeLabel) {
        this.seatLabel = placeLabel;
        this.seatNumber = placeNumber;
        this.comforts = null;
    }


    @NonNull
    public static Seat fromSeatNumber(Integer integer) {

        return new Seat(integer, integer.toString());
    }

    public static Set<Seat> generate(Integer placeCount) {

        Set<Integer> integers = new HashSet<>();
        for (int i = 0; i < placeCount; i++) {
            integers.add(i);
        }

        return integers.stream()
                .map(Seat::fromSeatNumber)
                .collect(Collectors.toSet());
    }


}
