package com.course_platform.courses.config;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class ConvertToken implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        var authorities = getAuthorities(source);
        return new JwtAuthenticationToken(source,authorities);
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Jwt jwt){
        var ra = new HashMap<>(jwt.getClaim("resource_access"));
        var account = (Map<String, List<String>>) ra.get("account");
        var roles = (List<String>) account.get("roles");
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
