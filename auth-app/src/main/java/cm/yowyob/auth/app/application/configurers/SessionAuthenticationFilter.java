package cm.yowyob.auth.app.application.configurers;


import cm.yowyob.auth.app.application.utils.HttpHelper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


public class SessionAuthenticationFilter  extends OncePerRequestFilter {


    private final JwtDecoder jwtDecoder;

    public SessionAuthenticationFilter(JwtDecoder jwtDecoder){
        super();
        this.jwtDecoder = jwtDecoder;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        UUID tenantId = HttpHelper.getTenantId(request);
        String sessionToken = HttpHelper.getSessionToken(request);

        if (sessionToken == null  || sessionToken.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }

        Jwt jwt = jwtDecoder.decode(sessionToken);

        String deviceId = jwt.getClaimAsString("deviceId");
        UUID sessionTokenTenantId = jwt.getClaimAsString("tenantId") == null ? null : UUID.fromString(jwt.getClaimAsString("tenantId"));
        List<String> roles = jwt.getClaimAsStringList("roles");

        if (roles == null || roles.isEmpty() || deviceId == null || !Objects.equals(sessionTokenTenantId, tenantId)){
            accessDenied(response, new Exception("invalid session"));
            return;
        }

        SessionAuthenticationToken auth = new SessionAuthenticationToken(
                roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()),
                sessionToken,
                UserWithDevice.builder()
                        .userId(jwt.getSubject())
                        .deviceId(deviceId)
                        .build()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }



    @SneakyThrows
    private static void accessDenied(HttpServletResponse response, Exception authException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{ \"error\": \"ACCESS_DENIED\", \"message\": \" " + authException.getMessage() + "\" }");
    }





}
