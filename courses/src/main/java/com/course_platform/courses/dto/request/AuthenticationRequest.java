package com.course_platform.courses.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class AuthenticationRequest {
    @Email
    private String email;

    @Size(min = 6,message = "PASSWORD_INVALID")
    private String password;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NoteUpdateRequest {
        private String note;
    }
}
