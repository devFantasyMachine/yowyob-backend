package cm.yowyob.auth.app.application.dto;


import cm.yowyob.auth.app.domain.model.tenant.LoginMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthdate;
    private String timezone;

}
