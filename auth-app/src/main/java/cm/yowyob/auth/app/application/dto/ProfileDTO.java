package cm.yowyob.auth.app.application.dto;

import cm.yowyob.auth.app.domain.model.contacts.Contact;
import cm.yowyob.auth.app.domain.model.user.Address;
import cm.yowyob.auth.app.domain.model.user.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private Integer version = 0;
    private String firstName;
    private String lastName;
    private String avatar;
    private String about;
    private String picture;
    private String gender;
    private String locale;
    private String timezone;
    private LocalDate birthdate;
    private Set<Contact> contacts;
    private Set<Address> address;

    public static ProfileDTO from(Profile profile){

        if (profile == null)
            return null;

        return ProfileDTO.builder()
                .version(profile.getVersion())
                .avatar(profile.getAvatar())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .gender(profile.getGender())
                .about(profile.getAbout())
                .picture(profile.getPicture())
                .birthdate(profile.getBirthdate())
                .timezone(profile.getTimezone() == null? null: profile.getTimezone().getId())
                .contacts(profile.getContacts())
                .address(profile.getAddress())
                .build();

    }


    public Profile toDomain() {

        return Profile.builder()
                .version(version)
                .avatar(avatar)
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .about(about)
                .picture(picture)
                .birthdate(birthdate)
                .timezone(timezone == null ? null : ZoneId.of(timezone))
                .contacts(contacts)
                .address(address)
                .build();
    }


}
