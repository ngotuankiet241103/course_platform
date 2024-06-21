package com.course_platform.courses.mapper;

import com.course_platform.courses.auth.UserPrincipal;
import com.course_platform.courses.dto.response.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserPrincipal userPrincipal);

}
