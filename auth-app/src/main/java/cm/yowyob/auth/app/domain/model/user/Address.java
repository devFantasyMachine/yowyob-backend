package cm.yowyob.auth.app.domain.model.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    public static final Address UNKNOWN = new Address();


    private String country;
    private String city;
    private String timezone;
    private String street;
    private String latitude;
    private String longitude;
    private String region;
    private String zipcode;


    public String getFormatted(){

        if (equals(UNKNOWN))
            return "unknown";

        return String.join(" - ", country, city, street);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return country.equals(address.country) && city.equals(address.city) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode);
    }


    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, zipcode);
    }


}
