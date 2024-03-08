package cm.yowyob.letsgo.driver.domain.model.resources;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Map;


@Getter
@Builder
@AllArgsConstructor
public class ClaimedResource {

    private final UserResource resource;

    @NonNull
    private final Map<String, List<Claim>> userClaims;

}
