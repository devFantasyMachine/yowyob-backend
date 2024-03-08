package cm.yowyob.letsgo.driver.application.dto;


import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.domain.model.identities.IdentityType;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HumanIdentityRequestDTO {

    @Nonnull
    private String identityUId;

    @Nonnull
    private IdentityType type;

    @Nonnull
    private LocalDate issueAt;

    @Nonnull
    private LocalDate expireAt;

    @Nonnull
    private String provider;

    private Set<String> docs;



    public HumanIdentity toObject() {

        return HumanIdentity.builder()
                .identityUId(this.getIdentityUId())
                .formattedIdentityProvider(provider)
                .docs(this.getDocs())
                .isVerified(false)
                .expireAt(this.getExpireAt())
                .issueAt(this.getIssueAt())
                .type(this.getType())
                .build();
    }



}
