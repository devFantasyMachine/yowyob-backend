package cm.yowyob.auth.app.domain.model.message.email;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {

    public String defaultFromName;
    public String defaultHtmlTemplate;
    public String defaultSubject;
    public String defaultTextTemplate;
    public String fromEmail;
    public UUID id;
    public ZonedDateTime insertInstant;
    public ZonedDateTime lastUpdateInstant;
    public String name;


}
