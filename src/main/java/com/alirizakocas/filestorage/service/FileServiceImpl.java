package com.alirizakocas.filestorage.service;

import com.alirizakocas.filestorage.error.FileException;
import com.alirizakocas.filestorage.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    //projede dosyanın kaydedileceği dizin
    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            if(!Files.isDirectory(root)) //
                throw new FileException("Could not initialize folder for upload!");
        }
    }

}
