package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.FileRequest;
import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String createFolder(String nameFolder) ;
    String uploadFile(FileRequest fileRequest);
    String uploadCloud(MultipartFile file) throws IOException;
    void updateFolder(String folderId, String newName);
}
