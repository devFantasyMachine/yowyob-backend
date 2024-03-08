package cm.yowyob.letsgo.driver.application.dto;

import cm.yowyob.letsgo.driver.domain.model.driver.DriverExperience;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Set;



@Data
@Builder
@AllArgsConstructor
public class DriverExperienceDTO {

    @Nonnull
    @NonNull
    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    private String label;

    @Nullable
    @Pattern(regexp = "^([A-Za-zÀ-ÿ]|(-)|(\\s)|(')|(@)|(\\.)|[0-9]|_|&|\\?|!|:)*$")
    private String desc;

    private LocalDate startAt;
    private LocalDate endAt;
    private Set<String> attachments;


    public static DriverExperienceDTO fromDomainObject(DriverExperience experience){

        return experience == null ? null : DriverExperienceDTO.builder()
                .label(experience.getLabel())
                .desc(experience.getDesc())
                .attachments(experience.getAttachments())
                .endAt(experience.getEndAt())
                .startAt(experience.getStarAt())
                .build();
    }


}
