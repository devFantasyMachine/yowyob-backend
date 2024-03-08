package cm.yowyob.auth.app.domain.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {

    private Boolean isSuccess = false;
    private String verificationId;

    @Builder.Default
    private Set<String> errors = new HashSet<>();

    public RegistrationResponse(Set<String> errors) {
        this(false, null, errors);
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public boolean hasError() {
        return !errors.isEmpty();
    }



}
