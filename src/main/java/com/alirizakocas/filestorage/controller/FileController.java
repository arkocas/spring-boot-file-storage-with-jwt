package com.alirizakocas.filestorage.controller;

import com.alirizakocas.filestorage.dto.FileDTO;
import com.alirizakocas.filestorage.model.File;
import com.alirizakocas.filestorage.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        File fileDetails = fileService.uploadFile(file);

        FileDTO fileDTO = new FileDTO(
                fileDetails.getFileId(),
                fileDetails.getFileName(),
                fileDetails.getFilePath(),
                fileDetails.getFileContentType(),
                fileDetails.getFileSize());

        return ResponseEntity.ok().body(fileDTO);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {

        File fileDetails = fileService.getFileById(fileId);
        Resource file = fileService.downloadFile(fileDetails);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(fileDetails.getFileContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity deleteFile(@PathVariable String fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/updateFileName")
    public ResponseEntity updateFileName(@RequestParam("fileId") String fileId, @RequestParam("fileName") String fileName) {

        File fileDetails = fileService.updateFileName(fileName, fileId);

        FileDTO fileDTO = new FileDTO(
                fileDetails.getFileId(),
                fileDetails.getFileName(),
                fileDetails.getFilePath(),
                fileDetails.getFileContentType(),
                fileDetails.getFileSize());

        return ResponseEntity.ok().body(fileDTO);
    }

    @GetMapping("/detail/{fileId}")
    public ResponseEntity getFileDetailById(@PathVariable String fileId) {

        File fileDetails = fileService.getFileById(fileId);

        FileDTO fileDTO = new FileDTO(
                fileDetails.getFileId(),
                fileDetails.getFileName(),
                fileDetails.getFilePath(),
                fileDetails.getFileContentType(),
                fileDetails.getFileSize());

        return ResponseEntity.ok().body(fileDTO);
    }
}
