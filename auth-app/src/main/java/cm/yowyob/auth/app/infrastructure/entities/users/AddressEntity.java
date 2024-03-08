package cm.yowyob.auth.app.infrastructure.entities.users;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddressEntity {


    private String country; // pays
    private String region; // continent
    private String state; // etat, province
    private String town; // city
    private String postalCode;
    private String street;
    private String timezone;


    @Data
    public static class AddressId {

        private String country;
        private String region;
        private String state;
        private String town;
        private String timezone;
    }



}




