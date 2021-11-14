package com.alirizakocas.filestorage;

import com.alirizakocas.filestorage.service.FileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class SpringBootFileStorageWithJwtApplication implements CommandLineRunner {
	@Resource
	FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFileStorageWithJwtApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		fileService.init(); // uploads klasörünün create edilmesi için
	}
}
