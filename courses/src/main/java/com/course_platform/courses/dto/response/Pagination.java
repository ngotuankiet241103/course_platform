package com.course_platform.courses.dto.response;

import lombok.*;


@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination {
    private int page;
    private int totalPage;
    private int totalElements;

    public int getPage() {
        return page + 1;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalElements() {
        return totalElements;
    }
}
