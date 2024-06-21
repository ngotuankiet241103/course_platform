package com.course_platform.courses.dto.response;

import com.google.api.client.util.DateTime;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Review {
    private String id;
    private String starRate;
    private String review;
    private UserInfo user;
    private Date createdDate;
}
