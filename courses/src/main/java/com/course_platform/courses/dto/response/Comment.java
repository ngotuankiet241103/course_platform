package com.course_platform.courses.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private String id;
    private UserInfo userInfo;
    private String content;
    private String rootId;
    private String parentId;
    private int nodeLeft;
    private int nodeRight;
    private Date createdDate;
}
