package com.alirizakocas.filestorage.controller;

import com.alirizakocas.filestorage.dto.FileDTO;
import com.alirizakocas.filestorage.model.File;
import com.alirizakocas.filestorage.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
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
@Api(value = "File Api controller documentation")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @ApiOperation(value = "file upload method")
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
    @ApiOperation(value = "file download method by fileId")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {

        File fileDetails = fileService.getFileById(fileId);
        Resource file = fileService.downloadFile(fileDetails);
        String fileName = FilenameUtils.getExtension(fileDetails.getFileName()).equals("") ? (fileDetails.getFileName() + "." + fileDetails.getFileExtension()) : fileDetails.getFileName();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(fileDetails.getFileContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(file);
    }

    @DeleteMapping("/delete/{fileId}")
    @ApiOperation(value = "file delete method by fileId")
    public ResponseEntity deleteFile(@PathVariable String fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/updateFileName")
    @ApiOperation(value = "filename update method by fileId")
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
    @ApiOperation(value = "fetch file detail method by fileId")
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
