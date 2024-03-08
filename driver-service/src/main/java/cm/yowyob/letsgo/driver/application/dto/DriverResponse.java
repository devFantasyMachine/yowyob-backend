package cm.yowyob.letsgo.driver.application.dto;


import cm.yowyob.letsgo.driver.domain.model.Address;
import cm.yowyob.letsgo.driver.domain.model.Contact;
import cm.yowyob.letsgo.driver.domain.model.driver.*;
import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.domain.model.resources.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
public class DriverResponse extends RepresentationModel<DriverResponse> {

    private String driverId;
    private Integer version;
    private String firstName;
    private String lastName;
    private String avatar;
    private String about;
    private String picture;
    private String gender;
    private LocalDate birthdate;
    private Set<Contact> contacts;
    private Score score;
    private Set<String> keywords;
    private Set<Address> address;
    private String businessName;
    private String businessCode;
    private String businessDescription;
    private String businessLogo;
    private String businessType;
    private String businessTaxationNumber;
    private String businessRegistrationNumber;
    private String businessWebSite;
    private Set<Contact> businessContacts;
    private Set<Address> businessAddress;
    private Boolean isEntreprise;
    private Integer yearsOfExperience;
    private String cvLink;
    private Set<String> docs;
    private Boolean isIndependent;
     private Set<DriverSkill> skills;
    private List<HumanIdentity> driverLicences;
    private List<DriverExperience> experiences;
    private Certificate certificate;
    private DriverPricing driverPricing;
    private Subscription subscription;
    private WorkZone workZone;

}
