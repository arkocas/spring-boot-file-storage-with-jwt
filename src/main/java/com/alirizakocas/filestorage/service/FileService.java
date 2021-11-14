package com.alirizakocas.filestorage.service;

import com.alirizakocas.filestorage.model.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void init();
    File getFileById(String id);
    File uploadFile(MultipartFile multipartFile);
    Resource downloadFile(File fileDetails);
    void deleteFile(String fileId);
    File updateFileName(String fileName, String fileId);
}
