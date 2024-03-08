package cm.yowyob.auth.app.infrastructure.entities.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserRecoveryConfigurationEntity {

    private Boolean recoveryEnabled;
    private String recoveryAccountId;
    private String recoveryEmailAddress;
    private String recoveryPhoneNumber;

    @ElementCollection
/*    @JoinTable(name="ATTRIBUTE_QUESTION_ANSWER", joinColumns={
            @JoinColumn(name="user_id", referencedColumnName="userId"),
            @JoinColumn(name="tenant_id", referencedColumnName="tenantId")})*/
    @MapKeyColumn (name="ANSWER_ID")
    @Column(name="QUESTION")
    private Map<String, String> questionsAnswers;

    private List<String> recoveryCodes;


}

