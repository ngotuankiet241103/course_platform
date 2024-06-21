package com.course_platform.courses.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class O2Auth {
    private String username;
    private String password;
    private String grant_type;
    private String client_id;

}
