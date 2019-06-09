package com.company.paw.payload.controllers;

import ch.qos.logback.core.util.FileUtil;
import com.company.paw.Repositories.ImageRepository;
import com.company.paw.models.Image;
import com.company.paw.payload.models.UploadFileResponse;
import com.company.paw.payload.services.FileStorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//todo do this controller via graphql

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
public class UploadController {
    private final FileStorageService fileStorageService;
    private final ImageRepository imageRepository;

    @PostMapping("/uploadFile")
    public Image uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            MultipartFile multiPartFile = new MockMultipartFile(FilenameUtils.getBaseName(file.getOriginalFilename()).concat(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()))+ "." + FilenameUtils.getExtension(file.getOriginalFilename()),file.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
//        file.getOriginalFilename().replace(file.getOriginalFilename(), FilenameUtils.getBaseName(file.getOriginalFilename()).concat(currentDate) + "." + FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase());
        String fileName = fileStorageService.storeFile(file);
        Image image = imageRepository.save(new Image(file.getOriginalFilename(), "/home/saeedhpro/upload/" + file.getOriginalFilename()));
        log.error(image.getId());
        return image;
    }

//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.stream(files)
//                .map(this::uploadFile)
//                .collect(Collectors.toList());
//    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content name
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file name.");
        }

        // Fallback to the default content name if name could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}