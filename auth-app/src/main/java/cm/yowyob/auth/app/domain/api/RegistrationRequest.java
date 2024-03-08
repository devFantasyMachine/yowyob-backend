package cm.yowyob.auth.app.domain.api;


import cm.yowyob.auth.app.domain.model.RequestDetails;
import cm.yowyob.auth.app.domain.model.tenant.LoginMethod;
import lombok.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;


@Getter
public class RegistrationRequest extends BaseRequest {

    private final String username;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final LocalDate birthdate;

    private final ZoneId timezone;
    private final LoginMethod method;

    public RegistrationRequest(@NonNull RequestDetails requestDetails,
                               String username,
                               String password,
                               String email,
                               String phoneNumber,
                               String firstName,
                               String lastName,
                               String gender,
                               LocalDate birthdate,
                               ZoneId timezone,
                               LoginMethod method) {
        super(requestDetails);

        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.timezone = timezone;
        this.method = method;
    }



}

