package cm.yowyob.auth.app.infrastructure;

import cm.yowyob.auth.app.domain.model.device.UserAgent;
import cm.yowyob.auth.app.domain.model.device.UserAgentParser;

import org.springframework.stereotype.Component;
import ua_parser.CachingParser;
import ua_parser.Client;


@Component
public class DefaultUserAgentParser implements UserAgentParser {

    private final CachingParser cachingParser = new CachingParser();

    @Override
    public UserAgent parse(String userAgent) {

        Client client = cachingParser.parse(userAgent);

        return new UserAgent(client.userAgent.family,
                client.device.family,
                client.os.family,
                client.os.major,
                userAgent);
    }


}
