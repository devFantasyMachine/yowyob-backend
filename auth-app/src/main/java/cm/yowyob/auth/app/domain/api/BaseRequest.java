package cm.yowyob.auth.app.domain.api;

import cm.yowyob.auth.app.domain.model.RequestDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;


@Data
@AllArgsConstructor
public abstract class BaseRequest {

    private final RequestDetails details;


    public UUID getTenantId(){
        return details.getTenantId();
    }

}

