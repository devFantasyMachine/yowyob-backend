/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.letsgo.driver.domain.model;


import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
public class Profile {

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


    public Profile(Integer version, String firstName, String lastName, String avatar, String about, String picture, String gender, LocalDate birthdate, Set<Contact> contacts, Set<Address> address, Set<String> keywords) {
        this.version = Objects.requireNonNullElse(version, 0);
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.about = about;
        this.picture = picture;
        this.gender = gender;
        this.birthdate = birthdate;
        this.contacts = contacts;
        this.address = address;
        this.keywords = Objects.requireNonNullElse(keywords, new HashSet<>());
    }

    public Profile() {
    }

    public static ProfileBuilder profileBuilder() {
        return new ProfileBuilder();
    }


    public static class ProfileBuilder {
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

        ProfileBuilder() {
        }

        public ProfileBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public ProfileBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ProfileBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ProfileBuilder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public ProfileBuilder about(String about) {
            this.about = about;
            return this;
        }

        public ProfileBuilder picture(String picture) {
            this.picture = picture;
            return this;
        }

        public ProfileBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public ProfileBuilder birthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
            return this;
        }

        public ProfileBuilder contacts(Set<Contact> contacts) {
            this.contacts = contacts;
            return this;
        }

        public ProfileBuilder address(Set<Address> address) {
            this.address = address;
            return this;
        }

        public ProfileBuilder keywords(Set<String> keywords) {
            this.keywords = keywords;
            return this;
        }

        public Profile build() {
            return new Profile(this.version, this.firstName, this.lastName, this.avatar, this.about, this.picture, this.gender, this.birthdate, this.contacts, this.address, this.keywords);
        }

        public String toString() {
            return "Profile.ProfileBuilder(version=" + this.version + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", avatar=" + this.avatar + ", about=" + this.about + ", picture=" + this.picture + ", gender=" + this.gender + ", birthdate=" + this.birthdate + ", contacts=" + this.contacts + ", address=" + this.address + ", keywords=" + this.keywords + ")";
        }
    }
}
