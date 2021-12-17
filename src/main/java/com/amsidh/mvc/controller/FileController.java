package com.amsidh.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

	String fileBasePath = "C:/Users/amsidh.lokhande/temp/upload/";
	private AtomicInteger atomicInteger = new AtomicInteger(0);

	@PostMapping("/upload")
	public ResponseEntity<byte[]> uploadToLocalFileSystem(@RequestParam("image") MultipartFile multipartFile) {
		System.out.println("Inside uploadToLocalFileSystem method of FileController. Count No- "+ atomicInteger.incrementAndGet());
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Path path = Paths.get(fileBasePath + fileName);
		try {
			Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try(InputStream in = new FileInputStream(new File(fileBasePath + fileName))){
			System.out.println("Returing response!!");
			return ResponseEntity.ok().body(IOUtils.toByteArray(in));
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(null);
		}
		
	}

}
