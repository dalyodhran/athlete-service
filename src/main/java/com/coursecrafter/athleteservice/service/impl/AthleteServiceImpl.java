package com.coursecrafter.athleteservice.service.impl;

import static org.reflections.Reflections.log;

import com.coursecrafter.athleteservice.domain.AthleteEntity;
import com.coursecrafter.athleteservice.repository.AthleteRepository;
import com.coursecrafter.athleteservice.service.AthleteService;
import com.coursecrafter.athleteservice.service.AvatarStorageService;
import com.coursecrafter.athleteservice.service.dto.AthleteDto;
import com.coursecrafter.athleteservice.service.dto.AthleteUpdateRequest;
import com.coursecrafter.athleteservice.service.dto.JwtUserIdentityDto;
import com.coursecrafter.athleteservice.service.mapper.AthleteMapper;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AthleteServiceImpl implements AthleteService {

    private final AthleteRepository athleteRepository;
    private final AvatarStorageService avatarStorageService;
    private final AthleteMapper athleteMapper;

    public AthleteServiceImpl(AthleteRepository athleteRepository, AthleteMapper athleteMapper, AvatarStorageService avatarStorageService) {
        this.athleteRepository = athleteRepository;
        this.avatarStorageService = avatarStorageService;
        this.athleteMapper = athleteMapper;
    }

    @Override
    @Transactional
    public AthleteDto getOrCreate(JwtUserIdentityDto jwtUserIdentityDto) {
        return athleteRepository
            .findByExternalId(jwtUserIdentityDto.getExternalId())
            .map(athleteMapper::toDto)
            .orElseGet(() -> {
                AthleteEntity athlete = AthleteEntity.fromJwtUserIdentity(jwtUserIdentityDto);
                AthleteEntity saved = athleteRepository.save(athlete);
                log.info("Saved Athlete entity: {}", saved);
                return athleteMapper.toDto(saved);
            });
    }

    @Override
    public AthleteDto partialUpdate(String id, AthleteUpdateRequest request, String externalId) {
        AthleteEntity entity = athleteRepository.findById(id).orElseThrow(() -> new RuntimeException("Athlete not found: " + id));

        if (!externalId.equals(entity.getExternalId())) {
            throw new RuntimeException("Forbidden: athlete does not belong to current user");
        }

        if (request.getFirstName() != null) {
            entity.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            entity.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            entity.setEmail(request.getEmail());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        if (request.getAvatarKey() != null) {
            entity.setAvatarKey(request.getAvatarKey());
        }
        if (request.getUnit() != null) {
            entity.setUnit(request.getUnit());
        }
        if (request.getDateOfBirth() != null) {
            entity.setDateOfBirth(request.getDateOfBirth());
        }

        entity.setUpdatedAt(Instant.now());

        AthleteEntity saved = athleteRepository.save(entity);
        return athleteMapper.toDto(saved);
    }

    @Override
    public AthleteDto updateAvatar(String athleteId, MultipartFile avatar) {
        AthleteEntity athlete = athleteRepository
            .findById(athleteId)
            .orElseThrow(() -> new IllegalArgumentException("Athlete not found: " + athleteId));

        // Store in S3 (LocalStack) and get back the URL or key
        String avatarPath = avatarStorageService.storeAvatar(athleteId, avatar);

        // Save on entity
        athlete.setAvatarKey(avatarPath);
        athlete.setUpdatedAt(Instant.now());

        AthleteEntity saved = athleteRepository.save(athlete);
        return athleteMapper.toDto(saved);
    }
}
