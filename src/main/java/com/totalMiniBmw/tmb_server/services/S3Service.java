package com.totalMiniBmw.tmb_server.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
public class S3Service {

    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(InputStream inputStream, String toolId, String contentType) throws IOException {
        // Generate a unique filename

        // Create the S3 PutObjectRequest
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("tmb-inventory")
                .key(toolId)
                .contentType(contentType)
                .build();

        // Upload the file
        s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, inputStream.available()));
    }
}
