package com.course_platform.courses.auth;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ConvertUserPrincipal implements Converter<JwtAuthenticationToken,UserPrincipal> {

    @Override
    public UserPrincipal convert(JwtAuthenticationToken source) {
        var claims = new HashMap<>(source.getToken().getClaims());

        return UserPrincipal.builder()
                .email(claims.get("email").toString())
                .name(claims.get("name").toString())
                .id(claims.get("sub").toString())
                .roles(new ArrayList<>(source.getAuthorities()).stream().map(Object::toString).toList())
                .build();
    }
}
