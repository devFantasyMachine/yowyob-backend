package cm.yowyob.letsgo.driver.infrastructure.entities;

import cm.yowyob.letsgo.driver.infrastructure.entities.udt.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;




@Data
@Builder
@AllArgsConstructor
@Table("drivers")
public class DriverEntity {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, ordinal = 0, name = "driver_id")
    private final String driverId;

    private Integer version;
    private String firstName;
    private String lastName;
    private String avatar;
    private String about;
    private String picture;
    private String gender;
    private LocalDateTime birthdate;
    private Set<ContactEntity> contacts;
    private ScoreEntity score;
    private Set<String> keywords;

    private Set<AddressEntity> address;

    private String businessName;
    private String businessCode;
    private String businessDescription;
    private String businessLogo;
    private String businessType;
    private String businessTaxationNumber;
    private String businessRegistrationNumber;
    private String businessWebSite;

    private Set<ContactEntity> businessContacts;
    private Set<AddressEntity> businessAddress;
    private Boolean isEntreprise;

    private Integer yearsOfExperience;
    private String cvLink;

    @Column("images")
    private Set<String> docs;

    private Boolean isIndependent;
    private Set<DriverSkillEntity> skills;
    @Column("licences")
    private Set<HumanIdentityEntity> driverLicences;
    private Set<DriverExperienceEntity> experiences;
    private CertificateEntity certificate;
    private DriverPricingEntity driverPricing;
    private WorkZoneEntity workZone;
    private DriverAvailabilityEntity availability;


}
