package com.course_platform.courses.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
    @NotEmpty(message = "TOKEN_INVALID")
    private String refreshToken;
}
