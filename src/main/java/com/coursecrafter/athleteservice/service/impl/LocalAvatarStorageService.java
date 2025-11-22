package com.coursecrafter.athleteservice.service.impl;

import com.coursecrafter.athleteservice.service.AvatarStorageService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class LocalAvatarStorageService implements AvatarStorageService {

    private final S3Client s3Client;

    @Value("${app.avatars.bucket-name}")
    private String bucketName;

    @Value("${app.avatars.base-url}")
    private String baseUrl;

    public LocalAvatarStorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String storeAvatar(String athleteId, MultipartFile file) {
        try {
            String ext = getExtension(file.getOriginalFilename());
            String key = "avatars/" + athleteId + ext;

            PutObjectRequest req = PutObjectRequest.builder().bucket(bucketName).key(key).contentType(file.getContentType()).build();

            s3Client.putObject(req, RequestBody.fromBytes(file.getBytes()));

            // LocalStack base URL is like:
            // http://localhost:4566/coursecrafter-avatars-local
            return baseUrl + "/" + key;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar", e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return ".png";
        return filename.substring(filename.lastIndexOf("."));
    }
}
