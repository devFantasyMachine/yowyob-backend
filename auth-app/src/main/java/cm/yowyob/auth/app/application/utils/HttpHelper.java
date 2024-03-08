package cm.yowyob.auth.app.application.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public abstract class HttpHelper {

    public static final String TENANT_HEADER = "X-Tenant-Id";
    public static final String DEVICE_HEADER = "X-Device-Id";
    public static final String API_KEY_HEADER = "X-Auth-Key";
    public static final String SESSION_TOKEN_HEADER = "X-Session-Token";




    public static String getIpAddress(HttpServletRequest request) {

        String clientIp;
        String clientXForwardedForIp = request.getHeader("x-forwarded-for");

        if (clientXForwardedForIp == null)
            clientXForwardedForIp = request.getHeader("X-Forwarded-For");

        if (clientXForwardedForIp != null) {
            clientIp = parseXForwardedHeader(clientXForwardedForIp);
        } else {
            clientIp = request.getRemoteAddr();
        }

        InetAddress inetAddress = null;
        try {

            inetAddress = InetAddress.getByName(clientIp);
            if (inetAddress.isLoopbackAddress()) {
                return "127.0.0.1";
            }

        } catch (UnknownHostException ignored) {}

        return clientIp;
    }



    private static String parseXForwardedHeader(String header) {
        return header.split(",")[0];
    }


    public static UUID getTenantId(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(TENANT_HEADER);
        return header == null ? null : UUID.fromString(header);
    }

    public static String getApiKey(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(API_KEY_HEADER);
    }


    public static UUID getDeviceId(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(DEVICE_HEADER);
        return header == null ? null : UUID.fromString(header);
    }

    public static String getSessionToken(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(SESSION_TOKEN_HEADER);
    }

}
