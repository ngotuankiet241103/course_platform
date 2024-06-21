package com.course_platform.courses.service.impl;

import com.cloudinary.Cloudinary;
import com.course_platform.courses.dto.request.FileRequest;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.service.FileService;
import com.course_platform.courses.utils.FileType;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final Drive drive;
    private final Cloudinary cloudinary;
    @Override
    public String createFolder(String nameFolder)  {
      try{
         File file = createFile(nameFolder, FileType.folderType);
          File createFolder = drive.files().create(file).setFields("id").execute();

          return  createFolder.getId();
      }
      catch (IOException exception){
          throw new CustomRuntimeException(ErrorCode.UNUPLOADFILE_EXCEPTION);
      }

    }
    private File createFile(String nameFolder,String type){
        File file = new File();
        file.setName(nameFolder);
        file.setMimeType(type);
        return  file;
    }
    @Override
    public String uploadFile(FileRequest fileRequest) {
        try {

            if (null != fileRequest.getFile()) {
                System.out.println(fileRequest.getFile().getOriginalFilename());
                File fileMetadata = new File();
                fileMetadata.setParents(Collections.singletonList(fileRequest.getFolderParentId()));
                fileMetadata.setName(fileRequest.getFile().getOriginalFilename());
                File uploadFile = drive
                        .files()
                        .create(fileMetadata, new InputStreamContent(
                                fileRequest.getFile().getContentType(),
                                new ByteArrayInputStream(fileRequest.getFile().getBytes()))
                        )

                        .setFields("id").execute();
                System.out.println(uploadFile);

                return uploadFile.getId();

            }
        } catch (Exception e) {
            throw new CustomRuntimeException(ErrorCode.UNUPLOADFILE_EXCEPTION);
        }
        return null;
    }

    @Override
    public String uploadCloud(MultipartFile file) throws IOException{

        return cloudinary.uploader()
                .upload(file.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }

    @Override
    public void updateFolder(String folderId, String newName) {
        try{
            File file = createFile(newName, FileType.folderType);
            drive.files().update(folderId,file).execute();
        }
        catch (IOException exception){
            throw new CustomRuntimeException(ErrorCode.UNUPLOADFILE_EXCEPTION);
        }
    }
}
