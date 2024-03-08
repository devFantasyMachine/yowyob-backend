package cm.yowyob.letsgo.driver.application.dto;

import cm.yowyob.letsgo.driver.domain.model.Contact;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrgencyContactDTO {

    @Nonnull
    @Pattern(regexp = Contact.PHONE_PATTERNS, message = "urgency contact value must be a valid phone number")
    private String phone;

    @Nonnull
    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    private String role;

}
