package com.alirizakocas.filestorage.service;

import com.alirizakocas.filestorage.error.FileException;
import com.alirizakocas.filestorage.error.ForbiddenException;
import com.alirizakocas.filestorage.error.NotFoundException;
import com.alirizakocas.filestorage.model.File;
import com.alirizakocas.filestorage.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;
    // projede dosyanın kaydedileceği dizin
    private final Path root = Paths.get("uploads");
    private static final List<String> mimeTypes = Arrays.asList("image/png",
            "image/jpeg",
            "image/jpg",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", //docx
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", //xlsx
            "application/pdf");

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            if(!Files.isDirectory(root)) //
                throw new FileException("Could not initialize folder for upload!");
        }
    }

    @Override
    public File getFileById(String id){
        return fileRepository.findById(id).orElseThrow(() -> new NotFoundException("File does not exist."));
    }

    @Transactional
    @Override
    public File uploadFile(MultipartFile multipartFile) throws FileException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(); //işlemi yapan kişi

        try {
            // dosya adının geçersiz karakterler içerip içermediğini kontrol ediliyor.
            if(fileName.contains(".."))
                throw new FileException("Invalid file name: " + fileName);
            // dosya uzantısının uygunluğu kontrol ediliyor.
            if(!isValidContentType(multipartFile.getContentType()))
                throw new FileException("Invalid file extension: " + fileName);

            // dosya bilgileri veritabanına kaydediliyor.
            File file = fileRepository.save(new File(authUsername, fileName, multipartFile.getContentType(),
                    FilenameUtils.getExtension(multipartFile.getOriginalFilename()), multipartFile.getSize(), this.root.toString()));
            // lokalimize fileId ve uzantısıyla kaydediliyor.
            Files.copy(multipartFile.getInputStream(), this.root.resolve(file.getFileId() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename())));
            return file;
        } catch (Exception ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteFile(String fileId) {
        try{
            File fileDetails = getFileById(fileId);
            isAuthorizedUser(fileDetails.getUsername());

            Path file = root.resolve(fileDetails.getFileId() + "." + fileDetails.getFileExtension());
            Files.delete(file); // dosyayı lokalden sil
            fileRepository.deleteById(fileId); // dosyaya ait bilgileri veritabanından sil

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Transactional
    @Override
    public File updateFileName(String fileName, String fileId) {
        try{
            File fileDetails = getFileById(fileId);
            isAuthorizedUser(fileDetails.getUsername());

            Optional<File> persistedFile = fileRepository.findById(fileId);
            if (persistedFile.isPresent()){
                File file = persistedFile.get();
                file.setFileName(fileName);
                fileRepository.save(file);
                return file;
            } else {
                throw new NotFoundException("File does not exist.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public Resource downloadFile(File fileDetails) {
        try {
            isAuthorizedUser(fileDetails.getUsername());

            Path file = root.resolve(fileDetails.getFileId() + "." + fileDetails.getFileExtension());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public boolean isValidContentType(String contentType){
        if(!mimeTypes.contains(contentType))
            return false;
        return true;
    }

    public void isAuthorizedUser(String fileUsername){
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(); // işlemi yapan kişi
        if(!authUsername.equals(fileUsername)) // işlemi yapan kişiyle dosya sahibi aynı değilse dosyaya erişemesin.
            throw new ForbiddenException("You are not authorized for this operation.");
    }

}
