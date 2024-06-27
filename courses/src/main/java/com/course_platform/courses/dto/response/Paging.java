package com.course_platform.courses.dto.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paging<T> {
    private List<T> data;
    private Pagination pagination;
}
