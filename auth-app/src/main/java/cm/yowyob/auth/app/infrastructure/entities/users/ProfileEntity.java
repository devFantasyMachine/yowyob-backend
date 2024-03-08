/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.infrastructure.entities.users;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProfileEntity {

    @Builder.Default
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
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

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ContactEntity> contacts = Set.of();

    @Builder.Default
    //@OneToMany(fetch = FetchType.EAGER)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<AddressEntity> address = Set.of();

}
