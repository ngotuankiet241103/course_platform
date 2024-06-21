package com.course_platform.courses.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}
