package com.course_platform.courses.controller;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteUpdateRequest {
    private String note;
}
