package com.totalMiniBmw.tmb_server.controller;

import com.totalMiniBmw.tmb_server.dto.responses.GenericActionResponse;
import com.totalMiniBmw.tmb_server.dto.responses.GenericActionType;
import com.totalMiniBmw.tmb_server.services.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }


    @PostMapping("/upload/image")
    public ResponseEntity<GenericActionResponse> uploadFile(@RequestParam("image") MultipartFile file, @RequestParam("toolId") String toolId) throws IOException {
            s3Service.uploadFile(file.getInputStream(), toolId, file.getContentType());
            GenericActionResponse gar = new GenericActionResponse("Attempted to upload image to MinIO S3.", null, GenericActionType.POST);

            return ResponseEntity.ok().body(gar);
    }

    @PostMapping("/upload/laser")
    public ResponseEntity<GenericActionResponse> uploadLaserFile(@RequestParam("file") MultipartFile file, @RequestParam("toolId") String toolId) throws IOException {
        s3Service.uploadFile(file.getInputStream(), toolId+"_LASER", file.getContentType());
        GenericActionResponse gar = new GenericActionResponse("Attempted to upload laser file to MinIO S3.", null, GenericActionType.POST);

        return ResponseEntity.ok().body(gar);
    }

}
