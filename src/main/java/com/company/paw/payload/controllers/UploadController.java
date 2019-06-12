package com.company.paw.payload.controllers;

import com.company.paw.Repositories.ImageRepository;
import com.company.paw.models.Image;
import com.company.paw.payload.services.FileStorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
public class UploadController {
    private final FileStorageService fileStorageService;
    private final ImageRepository imageRepository;

    @PostMapping("/uploadFile")
    public Image uploadFile(@RequestParam("file") MultipartFile file) {
        final String FILE_NAME = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-" + StringUtils.cleanPath(file.getOriginalFilename());
//        final String SERVER_PATH = "/home/saeedhpro/mac/paw/public";

        Image image = imageRepository.save(new Image(FILE_NAME, file.getOriginalFilename()));

        fileStorageService.storeFile(file, FILE_NAME);
        return image;
    }
}