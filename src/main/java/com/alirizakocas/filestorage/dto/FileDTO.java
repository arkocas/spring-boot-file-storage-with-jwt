package com.alirizakocas.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDTO {

    private String fileId;
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;
}
