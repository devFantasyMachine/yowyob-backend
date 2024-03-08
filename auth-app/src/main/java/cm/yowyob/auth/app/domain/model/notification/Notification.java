/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.domain.model.notification;


import cm.yowyob.auth.app.domain.model.user.UserId;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @NonNull
    private UserId userId;

    private String deviceId;
    private Duration ttl;
    private Map<String, String> data;
    @NonNull
    private String content;
    @NonNull
    private String subject;
    @NonNull
    private LocalDateTime createAt;
    @NonNull
    private NotificationType type;
    @NonNull
    private NotificationSeverity severity;

    private NotificationPriority priority;

}
