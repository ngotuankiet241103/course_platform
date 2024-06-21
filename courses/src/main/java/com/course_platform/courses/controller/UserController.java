package com.course_platform.courses.controller;

import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.User;
import com.course_platform.courses.entity.UserEntity;
import com.course_platform.courses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/info")
    public ApiResponse<User> getInfo(){
        return ApiResponse.<User>builder()
                .result(userService.getInfo())
                .build();
    }
    @PutMapping("/{user-id}/info")
    public ApiResponse<User> uploadAvatar(@PathVariable("user-id") String userId,
                                     @RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.<User>builder()
                .result(userService.updateAvatar(userId,file))
                .build();
    }
}
