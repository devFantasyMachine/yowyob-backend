package cm.yowyob.auth.app.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.UUID;





@Data
@EqualsAndHashCode
@AllArgsConstructor
public class UserId {

    @NonNull
    private String id;


    public static UserId fromUUID(@NonNull UUID uuid){

        return new UserId(uuid.toString());
    }


    public static UserId of(String userId) {

        if (userId == null)
            return null;

        return new UserId(userId);
    }

}
