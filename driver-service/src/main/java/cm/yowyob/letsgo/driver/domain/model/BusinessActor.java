/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.letsgo.driver.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessActor extends Profile {

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




    public BusinessActor(Integer version, String firstName, String lastName, String avatar, String about, String picture, String gender, LocalDate birthdate, Set<Contact> contacts, Set<Address> address, Set<String> keywords, String businessName, String businessCode, String businessDescription, String businessLogo, String businessType, String businessTaxationNumber, String businessRegistrationNumber, String businessWebSite, Set<Contact> businessContacts, Set<Address> businessAddress, Boolean isEntreprise, Integer yearsOfExperience, String cvLink, Set<String> docs, Boolean isIndependent) {
        super(version, firstName, lastName, avatar, about, picture, gender, birthdate, contacts, address, keywords);
        this.businessName = businessName;
        this.businessCode = businessCode;
        this.businessDescription = businessDescription;
        this.businessLogo = businessLogo;
        this.businessType = businessType;
        this.businessTaxationNumber = businessTaxationNumber;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.businessWebSite = businessWebSite;
        this.businessContacts = businessContacts;
        this.businessAddress = businessAddress;
        this.isEntreprise = isEntreprise;
        this.yearsOfExperience = yearsOfExperience;
        this.cvLink = cvLink;
        this.docs = docs;
        this.isIndependent = isIndependent;
    }


}
