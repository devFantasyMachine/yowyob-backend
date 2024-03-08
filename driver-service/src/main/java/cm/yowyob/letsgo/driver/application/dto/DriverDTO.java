/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.driver.application.dto;


import cm.yowyob.letsgo.driver.domain.model.Address;
import cm.yowyob.letsgo.driver.domain.model.Contact;
import cm.yowyob.letsgo.driver.domain.model.driver.*;
import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.domain.model.resources.Score;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;



@Data
@Builder
@AllArgsConstructor
public class DriverDTO {


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
    private Set<DriverSkillDTO> skills;
    private List<HumanIdentity> driverLicences;
    private List<DriverExperienceDTO> experiences;
    private Certificate certificate;
    private DriverPricingDTO driverPricing;
    private WorkZoneDTO workZone;
    private DriverAvailabilityDTO driverAvailability;

}

