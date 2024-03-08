package cm.yowyob.auth.app.domain.model.application;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scope {

    private String name;
    private String scopeDesc;
    private LocalDateTime createdAt;
    private UUID appId;
}
