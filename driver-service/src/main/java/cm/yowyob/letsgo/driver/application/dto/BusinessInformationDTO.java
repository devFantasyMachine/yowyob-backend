package cm.yowyob.letsgo.driver.application.dto;

import cm.yowyob.letsgo.driver.domain.model.Address;
import cm.yowyob.letsgo.driver.domain.model.Contact;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessInformationDTO {

    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    private String businessName;

    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    private String businessCode;

    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    private String businessDescription;

    private String businessLogo;
    private String businessType;

    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    private String businessTaxationNumber;

    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    private String businessRegistrationNumber;

    private String businessWebSite;

    private Set<Contact> businessContacts;
    private Set<Address> businessAddresses;
    private Boolean isEntreprise;
    private Integer yearsOfExperience;
    private String cvLink;
    private Set<String> docs;
    private Boolean isIndependent;

}
