/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.letsgo.driver.domain.model.driver;


import cm.yowyob.letsgo.driver.domain.model.Address;
import cm.yowyob.letsgo.driver.domain.model.Contact;
import cm.yowyob.letsgo.driver.domain.model.Profile;
import cm.yowyob.letsgo.driver.domain.model.BusinessActor;
import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.domain.model.resources.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Driver extends BusinessActor {

    private String driverId;
    private Score score;
    private Set<DriverSkill> skills;
    private List<DriverExperience> experiences;
    private List<HumanIdentity> driverLicences;
    private Certificate certificate;
    private DriverPricing driverPricing;
    private WorkZone workZone;
    private DriverAvailability driverAvailability;




    public Boolean isCertified() {

        return certificate != Certificate.NONE;
    }








    public Driver(Integer version, String firstName, String lastName, String avatar, String about, String picture, String gender, LocalDate birthdate, Set<Contact> contacts, Set<Address> address, Set<String> keywords, String businessName, String businessCode, String businessDescription, String businessLogo, String businessType, String businessTaxationNumber, String businessRegistrationNumber, String businessWebSite, Set<Contact> businessContacts, Set<Address> businessAddresses, Boolean isEntreprise, String plannerId, Score score, Boolean isIndependent, String cvLink, Integer yearsOfExperience, Set<String> docs, Set<DriverSkill> skills, List<DriverExperience> experiences, List<HumanIdentity> driverLicences, Certificate certificate, DriverPricing driverPricing, WorkZone workZone, DriverAvailability driverAvailability) {
        super(version, firstName, lastName, avatar, about, picture, gender, birthdate, contacts, address, keywords, businessName, businessCode, businessDescription, businessLogo, businessType, businessTaxationNumber, businessRegistrationNumber, businessWebSite, businessContacts, businessAddresses, false, yearsOfExperience, cvLink, docs, isIndependent);

        this.driverId = Objects.requireNonNull(plannerId);
        this.score = score;

        this.skills = skills;
        this.experiences = experiences;
        this.driverLicences = driverLicences;
        this.certificate = certificate;
        this.driverPricing = driverPricing;
        this.workZone = workZone;
        this.driverAvailability = driverAvailability;
    }


    public DriverInfo getDriverInfo() {

        return DriverInfo.builder()
                .driverId(this.getDriverId())
                .ownerId(this.getDriverId())
                .version(this.getVersion())
                .score(this.getScore())
                .avatar(this.getAvatar())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .gender(this.getGender())
                .picture(this.getPicture())
                .pseudo(this.getBusinessName())
                .docs(getDocs())
                .driverLicences(this.getDriverLicences())
                .skills(skills)
                .isIndependent(getIsIndependent())
                .experiences(experiences)
                .build();
    }



    public static DriverBuilder builder() {
        return new DriverBuilder();
    }


    public static class DriverBuilder {

        private String driverId;
        private Score score;
        private Integer version;
        private String firstName;
        private String lastName;
        private String avatar;
        private String about;
        private String picture;
        private String gender;
        private LocalDate birthdate;
        private Set<Contact> contacts;
        private Set<Address> address;
        private Set<String> keywords;
        private String businessName;
        private String businessCode;
        private String businessDescription;
        private String businessLogo;
        private String businessType;
        private String businessTaxationNumber;
        private String businessRegistrationNumber;
        private String businessWebSite;
        private Set<Contact> businessContacts;
        private Set<Address> businessAddresses;
        private Boolean isEntreprise = false;

        private Boolean isIndependent = true;
        private String cvLink = null;
        private Integer yearsOfExperience = 0;
        private Set<String> docs = Set.of();
        private Set<DriverSkill> skills = Set.of();
        private List<DriverExperience> experiences = List.of();
        private List<HumanIdentity> driverLicences = List.of();
        private Certificate certificate = Certificate.NONE;
        private DriverPricing driverPricing = DriverPricing.NONE;
        private WorkZone workZone;
        private DriverAvailability driverAvailability;



        DriverBuilder() {
        }


        public DriverBuilder driverAvailability(DriverAvailability driverAvailability) {
            this.driverAvailability = driverAvailability;
            return this;
        }

        public DriverBuilder WorkZone(WorkZone workZone) {
            this.workZone = workZone;
            return this;
        }

        public DriverBuilder score(Score score) {
            this.score = score;
            return this;
        }


        public DriverBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public DriverBuilder driverId(String driverId) {
            this.driverId = driverId;
            return this;
        }

        public DriverBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public DriverBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public DriverBuilder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public DriverBuilder about(String about) {
            this.about = about;
            return this;
        }

        public DriverBuilder picture(String picture) {
            this.picture = picture;
            return this;
        }

        public DriverBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public DriverBuilder birthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public DriverBuilder contacts(Set<Contact> contacts) {
            this.contacts = contacts;
            return this;
        }

        public DriverBuilder address(Set<Address> address) {
            this.address = address;
            return this;
        }

        public DriverBuilder keywords(Set<String> keywords) {
            this.keywords = keywords;
            return this;
        }

        public DriverBuilder isIndependent(Boolean isIndependent) {
            this.isIndependent = isIndependent;
            return this;
        }

        public DriverBuilder cvLink(String cvLink) {
            this.cvLink = cvLink;
            return this;
        }

        public DriverBuilder yearsOfExperience(Integer yearsOfExperience) {
            this.yearsOfExperience = yearsOfExperience;
            return this;
        }
        public DriverBuilder docs(Set<String> docs) {
            this.docs = docs;
            return this;
        }
        public DriverBuilder skills(Set<DriverSkill> skills) {
            this.skills = skills;
            return this;
        }
        public DriverBuilder experiences(List<DriverExperience> experiences) {
            this.experiences = experiences;
            return this;
        }


        public DriverBuilder driverLicences(List<HumanIdentity> driverLicences) {
            this.driverLicences = driverLicences;
            return this;
        }


        public DriverBuilder certificate(Certificate certificate) {
            this.certificate = certificate;
            return this;
        }
        public DriverBuilder driverPricing(DriverPricing driverPricing) {
            this.driverPricing = driverPricing;
            return this;
        }

        public DriverBuilder businessName(String businessName) {
            this.businessName = businessName;
            return this;
        }

        public DriverBuilder businessCode(String businessCode) {
            this.businessCode = businessCode;
            return this;
        }

        public DriverBuilder businessDescription(String businessDescription) {
            this.businessDescription = businessDescription;
            return this;
        }

        public DriverBuilder businessLogo(String businessLogo) {
            this.businessLogo = businessLogo;
            return this;
        }

        public DriverBuilder businessType(String businessType) {
            this.businessType = businessType;
            return this;
        }

        public DriverBuilder businessTaxationNumber(String businessTaxationNumber) {
            this.businessTaxationNumber = businessTaxationNumber;
            return this;
        }

        public DriverBuilder businessRegistrationNumber(String businessRegistrationNumber) {
            this.businessRegistrationNumber = businessRegistrationNumber;
            return this;
        }

        public DriverBuilder businessWebSite(String businessWebSite) {
            this.businessWebSite = businessWebSite;
            return this;
        }

        public DriverBuilder businessContacts(Set<Contact> businessContacts) {
            this.businessContacts = businessContacts;
            return this;
        }

        public DriverBuilder businessAddresses(Set<Address> businessAddresses) {
            this.businessAddresses = businessAddresses;
            return this;
        }

        public DriverBuilder isEntreprise(Boolean isEntreprise) {
            this.isEntreprise = isEntreprise;
            return this;
        }

        public DriverBuilder profile(Profile profile) {

            return this.avatar(profile.getAvatar())
                    .avatar(profile.getAvatar())
                    .about(profile.getAbout())
                    .address(profile.getAddress())
                    .gender(profile.getGender())
                    .birthdate(profile.getBirthdate())
                    .keywords(profile.getKeywords())
                    .version(profile.getVersion())
                    .picture(profile.getPicture())
                    .lastName(profile.getLastName())
                    .firstName(profile.getFirstName())
                    .contacts(profile.getContacts());
        }


        public Driver build() {

            return new Driver(version,
                    firstName,
                    lastName,
                    avatar,
                    about,
                    picture,
                    gender,
                    birthdate,
                    contacts,
                    address,
                    keywords,
                    businessName,
                    businessCode,
                    businessDescription,
                    businessLogo,
                    businessType,
                    businessTaxationNumber,
                    businessRegistrationNumber,
                    businessWebSite,
                    businessContacts,
                    businessAddresses,
                    isEntreprise,
                    driverId,
                    score,
                    isIndependent,
                    cvLink,
                    yearsOfExperience,
                    docs,
                    skills,
                    experiences,
                    driverLicences,
                    certificate,
                    driverPricing,
                    workZone, driverAvailability);
        }


        public String toString() {
            return "Planner.DriverBuilder(score=" + this.score + ")";
        }

    }


}

