package com.course_platform.courses.service.impl;

import com.course_platform.courses.auth.UserPrincipal;
import com.course_platform.courses.dto.response.User;
import com.course_platform.courses.entity.UserEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.mapper.UserMapper;
import com.course_platform.courses.repository.UserRepository;
import com.course_platform.courses.service.FileService;
import com.course_platform.courses.service.RoleService;
import com.course_platform.courses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Converter<JwtAuthenticationToken, UserPrincipal> converter;
    private final FileService fileService;
//    @PostConstruct
//    void initUser(){
//        List<UserEntity> users = List.of(
//                new UserEntity("ngotuankiet12347@gmail.com","123456","Ngo Tuan Kiet"),
//                new UserEntity("ntk241103@gmail.com","123456","Lionel Messi")
//        );
//        users.stream()
//                .forEach(userRepository::save);
//    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getInfo() {
        UserPrincipal userPrincipal = converter.convert((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        String image  = userRepository.findById(userPrincipal.getId()).map(userEntity -> userEntity.getAvatar()).orElse(null);
        userPrincipal.setAvatar(image);
        return userMapper.toUser(userPrincipal);
    }

    @Override
    public User updateAvatar(String userId, MultipartFile file) throws IOException {
        if(file == null) throw  new CustomRuntimeException(ErrorCode.FILE_INVALID);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
        String avatar = fileService.uploadCloud(file);
        user.setAvatar(avatar);
        return mappingOne(userRepository.save(user));
    }

    @Override
    public List<User> mappingList(List<UserEntity> users){

        return users.stream()
                .map(this::mappingOne)
                .toList();
    }
    @Override
    public User mappingOne(UserEntity userEntity){
        UserPrincipal userPrincipal = converter.convert((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        userPrincipal.setAvatar(userEntity.getAvatar());

        return userMapper.toUser(userPrincipal);

    }
}
