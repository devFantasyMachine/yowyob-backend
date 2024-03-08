package cm.yowyob.auth.app.domain.model.registration;


import cm.yowyob.auth.app.domain.model.TimedChallenge;

import cm.yowyob.auth.app.domain.model.tenant.LoginMethod;
import cm.yowyob.auth.app.domain.model.user.RequireAction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;



@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Registration extends TimedChallenge {

    private final String username;
    private final String encodedPassword;
    private final String email;
    private final String phone;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final LocalDate birthdate;
    private final ZoneId timezone;

    private final String userAgent;
    private final String ipAddress;

    private final String registrationCode;
    private LoginMethod loginMethod;
    private String redirectUri;
    private Set<RequireAction> requireActions;

    public Registration(String id, UUID tenantId, @NonNull Instant expireAt, String username, String encodedPassword, String email, String phone, String firstName, String lastName, String gender, LocalDate birthdate, ZoneId timezone, String userAgent, String ipAddress, String registrationCode) {
        super(id, tenantId, expireAt);
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.timezone = timezone;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
        this.registrationCode = registrationCode;
        this.requireActions = new HashSet<>();
    }

    public Registration(String id, UUID tenantId, Instant issueAt, Instant expireAt, Boolean used, Boolean isAccepted, Instant acceptedAt, String username, String encodedPassword, String email, String phone, String firstName, String lastName, String gender, LocalDate birthdate, ZoneId timezone, String userAgent, String ipAddress, String registrationCode, LoginMethod loginMethod, String redirectUri, Set<RequireAction> requireActions) {
        super(id, tenantId, issueAt, expireAt, used, isAccepted, acceptedAt);
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.timezone = timezone;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
        this.registrationCode = registrationCode;
        this.loginMethod = loginMethod;
        this.redirectUri = redirectUri;
        this.requireActions = requireActions;
    }


    public void removeAction(RequireAction requireAction) {

        if (requireActions == null)
            return;

        requireActions = new HashSet<>(requireActions);
        requireActions.remove(requireAction);
    }

}
