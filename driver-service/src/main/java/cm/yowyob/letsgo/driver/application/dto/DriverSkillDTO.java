package cm.yowyob.letsgo.driver.application.dto;


import cm.yowyob.letsgo.driver.domain.model.driver.DriverSkill;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DriverSkillDTO {

    @Nonnull
    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    String name;

    @Nullable
    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    String desc;


    public static DriverSkillDTO fromDomainObject(DriverSkill skill){

        return skill == null ? null : new DriverSkillDTO(skill.name(), skill.desc());
    }


}
