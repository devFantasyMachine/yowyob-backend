package cm.yowyob.letsgo.driver.application.dto;

import cm.yowyob.letsgo.driver.domain.model.identities.HumanIdentity;
import cm.yowyob.letsgo.driver.domain.model.identities.IdentityType;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
public class HumanIdentityDTO {

    @Nonnull
    private String identityUId;

    @Nonnull
    private IdentityType type;

    private Boolean isVerified;

    @Nonnull
    private LocalDate issueAt;

    @Nonnull
    private LocalDate expireAt;

    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;

    @Nonnull
    private UUID providerId;
    private String providerName;
    private Set<String> docs;

    public HumanIdentity toObject() {

        return HumanIdentity.builder()
                .identityUId(identityUId)
                .providerId(providerId)
                .docs(this.getDocs())
                .isVerified(this.getIsVerified())
                .expireAt(this.getExpireAt())
                .issueAt(this.getIssueAt())
                .type(this.getType())
                .createdAt(this.getCreatedAt())
                .verifiedAt(this.getVerifiedAt())
                .build();
    }


}
