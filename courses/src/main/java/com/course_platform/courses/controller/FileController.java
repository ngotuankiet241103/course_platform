package com.course_platform.courses.controller;

import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @PostMapping("/upload")
    ApiResponse<String> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        if(file == null) throw new CustomRuntimeException(ErrorCode.FILE_INVALID);
        return ApiResponse.<String>builder()
                .result(fileService.uploadCloud(file))
                .build();

    }
}
