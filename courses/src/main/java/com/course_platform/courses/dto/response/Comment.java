package com.course_platform.courses.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Comment {
    private String id;
    private UserInfo userInfo;
    private String content;
    private String rootId;
    private String parentId;
    private String nodeLeft;
    private String nodeRight;
    private Date createdDate;
}
