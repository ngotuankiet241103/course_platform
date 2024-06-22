package com.course_platform.courses.dto.response;

import com.google.api.client.util.DateTime;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private String id;
    private int starRate;
    private String review;
    private UserInfo user;
    private Date createdDate;
}
