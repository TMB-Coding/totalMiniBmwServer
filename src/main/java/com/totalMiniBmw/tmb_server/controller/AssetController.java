package com.totalMiniBmw.tmb_server.controller;

import com.totalMiniBmw.tmb_server.services.S3Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private final S3Service s3Service;

    public AssetController(S3Service s3Service) {
        this.s3Service = s3Service;
    }


    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(
            @RequestParam String objectKey) {
        return s3Service.downloadFile(objectKey);
    }
}
