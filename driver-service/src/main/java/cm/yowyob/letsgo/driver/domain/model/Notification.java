/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.letsgo.driver.domain.model;


import lombok.*;

import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @NonNull
    private String userId;
    @NonNull
    private String tenantId;
    private String deviceId;
    private Long ttl;
    private Map<String, String> data;
    @NonNull
    private String content;
    @NonNull
    private String subject;
    @NonNull
    private Long createAt;
    @NonNull
    private NotificationType type;
    @NonNull
    private NotificationSeverity severity;

}
