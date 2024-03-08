 /* Copyright (c) 2023.
 * By Yowyob. @Author FantasyMachine
 */



package cm.yowyob.letsgo.trip.domain.model.vehicle;

 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class LuggageBox  {

    private final String boxLabel;
    private final Integer boxNumber;
    private final Float boxQuantityKg;

}

