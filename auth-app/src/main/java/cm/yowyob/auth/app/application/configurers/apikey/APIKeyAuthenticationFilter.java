/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.yowyob.auth.app.application.configurers.apikey;

import cm.yowyob.auth.app.application.utils.HttpHelper;
import cm.yowyob.auth.app.domain.handlers.APIKeyManager;
import cm.yowyob.auth.app.domain.model.token.APIKey;
import cm.yowyob.auth.app.domain.model.token.Permission;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;


public final class APIKeyAuthenticationFilter extends OncePerRequestFilter {


    private static final Map<Permission, AntPathRequestMatcher> config = new HashMap<>();

    static {

        config.put(Permission.TENANT_READ, new AntPathRequestMatcher("/tenants/**", "GET"));
        config.put(Permission.TENANT_CREATE, new AntPathRequestMatcher("/tenants/**", "POST"));
        config.put(Permission.TENANT_UPDATE, new AntPathRequestMatcher("/tenants/**", "PUT"));

        config.put(Permission.REGISTRATION_CREATE, new AntPathRequestMatcher("/registrations/**", "POST"));
        config.put(Permission.AUTH_CREATE, new AntPathRequestMatcher("/auth/**", "POST"));

    }



    private final APIKeyManager apiKeyManager;

    public APIKeyAuthenticationFilter(APIKeyManager apiKeyManager) {
        this.apiKeyManager = Objects.requireNonNull(apiKeyManager);
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        UUID tenantId = HttpHelper.getTenantId(request);
        String keyStr = HttpHelper.getApiKey(request);

        APIKey apiKey = keyStr == null ? null : apiKeyManager.getKey(keyStr).orElse(null);

        if (tenantId == null || apiKey == null) {

            accessDenied(response, new Exception("UNKNOWN_CLIENT"));
            return;
        }

        String ipAddress = HttpHelper.getIpAddress(request);

        if (!apiKey.getEnabled()
                || !apiKey.getIpAccessControlList().contains(ipAddress)
                || !apiKey.getTenantId().equals(tenantId)) {

            accessDenied(response, new Exception("UNAUTHORIZED_CLIENT"));
            return;
        }

        Set<Permission> permissions = apiKey.getPermissions();
        Set<Permission> requiredPermissions = getRequiredPermissions(request);

        if (requiredPermissions.isEmpty() || permissions.containsAll(requiredPermissions)){

            filterChain.doFilter(request, response);
            return;
        }

        accessDenied(response, new Exception("NO_PERMISSION"));

    }


    private static Set<Permission> getRequiredPermissions(HttpServletRequest request) {

        Set<Permission> requiredPermissions = new HashSet<>();
        config.forEach((permission, antPathRequestMatcher) -> {

            if (antPathRequestMatcher.matches(request))
                requiredPermissions.add(permission);

        });
        return requiredPermissions;
    }


    @SneakyThrows
    private static void accessDenied(HttpServletResponse response, Exception authException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{ \"error\": \"ACCESS_DENIED\", \"message\": \" " + authException.getMessage() + "\" }");
    }


}
