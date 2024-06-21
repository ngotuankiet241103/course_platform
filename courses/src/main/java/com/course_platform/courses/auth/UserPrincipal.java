package com.course_platform.courses.auth;


import com.course_platform.courses.entity.PermissionEntity;
import com.course_platform.courses.entity.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal {
    private String id;
    private String email;
    private String name;
    private String avatar;
    private List<String> roles;

}
