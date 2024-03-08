/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.domain.model.user;


import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.util.ObjectUtils;
import lombok.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    public static final Profile EMPTY;

    static {

        EMPTY = Profile.builder().build();
    }


    @NonNull
    @Builder.Default
    private Integer version = 0;
    private String firstName;
    private String lastName;
    private String avatar;
    private String about;
    private String picture;
    private String gender;
    private ZoneId timezone;
    private LocalDate birthdate;
    private Set<Contact> contacts;
    private Set<Address> address;
    private List<Locale> preferredLanguages;



    public Profile merge(Profile profile) {

        return Profile.builder()
                .version(version + 1)
                .avatar(ObjectUtils.getOrDefault(profile.avatar, avatar))
                .firstName(ObjectUtils.getOrDefault(profile.firstName, firstName))
                .lastName(ObjectUtils.getOrDefault(profile.lastName, lastName))
                .gender(ObjectUtils.getOrDefault(profile.gender, gender))
                .about(ObjectUtils.getOrDefault(profile.about, about))
                .picture(ObjectUtils.getOrDefault(profile.picture, picture))
                .birthdate(ObjectUtils.getOrDefault(profile.birthdate, birthdate))
                .timezone(ObjectUtils.getOrDefault(profile.timezone, timezone))
                .contacts(ObjectUtils.getOrDefault(profile.contacts, contacts))
                .address(ObjectUtils.getOrDefault(profile.address, address))
                .preferredLanguages(ObjectUtils.getOrDefault(profile.preferredLanguages, preferredLanguages))
                .build();
    }

}
