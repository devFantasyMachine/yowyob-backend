package cm.yowyob.letsgo.driver.domain.model.identities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HumanIdentity {

    private String identityUId;
    private IdentityType type;
    private Boolean isVerified;
    private LocalDate issueAt;
    private LocalDate expireAt;
    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;
    private String formattedIdentityProvider;
    private UUID providerId;
    private Set<String> docs = new HashSet<>();

    public HumanIdentity(String identityUId, IdentityType permit, LocalDate issueAt, LocalDate expireAt, String formattedIdentityProvider, Set<String> docs) {
        this.identityUId = identityUId;
        this.type = permit;
        this.isVerified = false;
        this.issueAt = issueAt;
        this.expireAt = expireAt;
        this.createdAt = LocalDateTime.now();
        this.verifiedAt = null;
        this.formattedIdentityProvider = formattedIdentityProvider;
        this.docs.addAll(docs);
    }


}
