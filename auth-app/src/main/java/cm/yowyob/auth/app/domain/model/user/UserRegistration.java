package cm.yowyob.auth.app.domain.model.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.ZonedDateTime;
import java.util.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistration {

    private UUID id;
    private UUID applicationId;
    private String authenticationToken;
    private ZonedDateTime insertInstant;
    private ZonedDateTime lastLoginInstant;
    private ZonedDateTime lastUpdateInstant;

    private Map<String, String> data;

    private User user;


    @Builder.Default
    public Set<String> roles = new HashSet<>();

}

