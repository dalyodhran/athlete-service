package com.coursecrafter.athleteservice.service;

import com.coursecrafter.athleteservice.domain.AthleteEntity;
import com.coursecrafter.athleteservice.service.dto.AthleteDto;
import com.coursecrafter.athleteservice.service.dto.AthleteUpdateRequest;
import com.coursecrafter.athleteservice.service.dto.JwtUserIdentityDto;
import org.springframework.web.multipart.MultipartFile;

public interface AthleteService {
    AthleteDto getOrCreate(JwtUserIdentityDto jwtUserIdentityDto);
    AthleteDto partialUpdate(String id, AthleteUpdateRequest request, String externalId);
    AthleteDto updateAvatar(String athleteId, MultipartFile avatar);
}
