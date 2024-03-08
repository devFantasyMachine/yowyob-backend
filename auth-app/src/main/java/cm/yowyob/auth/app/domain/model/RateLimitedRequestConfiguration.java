package cm.yowyob.auth.app.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateLimitedRequestConfiguration {

    private boolean isEnabled;
    private int limit;
    private int timePeriodInSeconds;
    private RequestType type;

    public RateLimitedRequestConfiguration(int limit, int period) {
        this.limit = limit;
        this.timePeriodInSeconds = period;
        this.type = RequestType.IP;
    }


    public enum RequestType {

        IP,
        USER
    }


}
