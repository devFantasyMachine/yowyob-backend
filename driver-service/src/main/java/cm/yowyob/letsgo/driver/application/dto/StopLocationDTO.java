package cm.yowyob.letsgo.driver.application.dto;


import cm.yowyob.letsgo.driver.domain.model.stops.StopLocation;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StopLocationDTO {


    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    String name;

    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    String city;



    public static StopLocationDTO fromDomainObject(StopLocation stopLocation){

        return  stopLocation == null ? null : StopLocationDTO.builder()
                .name(stopLocation.name())
                .city(stopLocation.city())
                .build();
    }



    public StopLocation toDomainObject(){

        return StopLocation.builder()
                .city(city)
                .name(name)
                .build();
    }
}
