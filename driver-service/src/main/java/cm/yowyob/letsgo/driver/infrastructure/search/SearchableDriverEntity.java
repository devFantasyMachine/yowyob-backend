package cm.yowyob.letsgo.driver.infrastructure.search;


import cm.yowyob.letsgo.driver.domain.model.Address;
import cm.yowyob.letsgo.driver.domain.model.Contact;
import cm.yowyob.letsgo.driver.domain.model.driver.*;
import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.domain.model.resources.Score;
import cm.yowyob.letsgo.driver.infrastructure.entities.udt.DriverPricingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;



@Data
@Builder
@AllArgsConstructor
@Document(indexName = "letsgo_driver")
public class SearchableDriverEntity {

    @Id
    private String driverId;

    private Integer version;
    @Field(type = FieldType.Text)
    private String firstName;
    @Field(type = FieldType.Text)
    private String lastName;
    private String avatar;
    private String about;
    private String picture;
    @Field(type = FieldType.Keyword)
    private String gender;
    private LocalDate birthdate;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Set<Contact> contacts;
    private Score score;
    private Set<String> keywords;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Set<Address> address;

    @Field(type = FieldType.Text)
    private String businessName;
    @Field(type = FieldType.Text)
    private String businessCode;
    @Field(type = FieldType.Text)
    private String businessDescription;
    private String businessLogo;
    private String businessType;
    private String businessTaxationNumber;
    private String businessRegistrationNumber;
    private String businessWebSite;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Set<Contact> businessContacts;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Set<Address> businessAddress;
    private Boolean isEntreprise;

    private Integer yearsOfExperience;
    private String cvLink;
    private Set<String> docs;
    private Boolean isIndependent;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Set<DriverSkill> skills;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<HumanIdentity> driverLicences;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<DriverExperience> experiences;

    private Certificate certificate;
    private DriverPricingEntity driverPricing;
    private Subscription subscription;
    private WorkZone workZone;
    private DriverAvailability driverAvailability;




}
