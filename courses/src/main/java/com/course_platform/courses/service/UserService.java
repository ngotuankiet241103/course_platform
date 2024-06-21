package com.course_platform.courses.service;

import com.course_platform.courses.dto.response.User;
import com.course_platform.courses.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService extends BaseService<UserEntity, User>{
    List<UserEntity> findAll();

    User getInfo();

    User updateAvatar(String userId, MultipartFile file) throws IOException;
}
