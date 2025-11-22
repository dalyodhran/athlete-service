package com.coursecrafter.athleteservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarStorageService {
    String storeAvatar(String athleteId, MultipartFile file);
}
