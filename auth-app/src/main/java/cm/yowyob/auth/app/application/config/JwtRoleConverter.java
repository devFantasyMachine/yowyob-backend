package cm.yowyob.auth.app.application.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        @SuppressWarnings("unchecked")
        List<String> roles = (ArrayList<String>) jwt.getClaims().get("roles");


        @SuppressWarnings("unchecked")
        List<String> scopes = (ArrayList<String>) jwt.getClaims().getOrDefault("scope", List.of());

        if (roles == null || roles.isEmpty()) {
            return new ArrayList<>();
        }

        if (scopes == null || scopes.isEmpty()){
            scopes = List.of();
        }

        return Stream.concat(scopes.stream(), roles.stream())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }



}