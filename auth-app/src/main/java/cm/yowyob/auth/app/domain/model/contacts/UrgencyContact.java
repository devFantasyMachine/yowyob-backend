package cm.yowyob.auth.app.domain.model.contacts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class UrgencyContact {

    private Contact contact;
    private String role;

    public UrgencyContact(String roleName, PhoneNumber phoneNumber) {
        this.contact = phoneNumber;
        this.role = roleName;
    }


}
