package cm.yowyob.letsgo.driver.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
@Builder
@AllArgsConstructor
public class Address {

    private final String continent;
    private final String country; // pays
    private final String region; // continent
    private final String state; // etat, province
    private final String town; // city
    private final String timezone;
    private String street;
    private String postalCode;
    private Boolean isCurrent;
    private Double lat;
    private Double lon;
    private final LocalDateTime addAt;


    public String getFormatted(){

        return region + ", " + country + ", " + state + ", " + town + ", " + street ;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return country.equals(address.country) && region.equals(address.region) && state.equals(address.state) && town.equals(address.town) && Objects.equals(street, address.street) && Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, region, state, town, street, postalCode);
    }
}




