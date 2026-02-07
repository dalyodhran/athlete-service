package com.coursecrafter.athleteservice.contorller;

import com.coursecrafter.athleteservice.service.dto.AthleteDto;
import com.coursecrafter.athleteservice.service.dto.AthleteUpdateRequest;
import com.coursecrafter.athleteservice.service.dto.JwtUserIdentityDto;
import com.coursecrafter.athleteservice.service.impl.AthleteServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/athletes")
public class AthleteController {

    private final AthleteServiceImpl athleteServiceImpl;

    public AthleteController(AthleteServiceImpl athleteServiceImpl) {
        this.athleteServiceImpl = athleteServiceImpl;
    }

    @PutMapping("/me")
    public ResponseEntity<AthleteDto> getOrCreate(@AuthenticationPrincipal Jwt jwt) {
        JwtUserIdentityDto jwtUserIdentityDto = new JwtUserIdentityDto(
            jwt.getSubject(),
            jwt.getClaimAsString("given_name"),
            jwt.getClaimAsString("family_name"),
            jwt.getClaimAsString("email")
        );

        return ResponseEntity.ok(athleteServiceImpl.getOrCreate(jwtUserIdentityDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AthleteDto> partialUpdateAthlete(
        @PathVariable String id,
        @RequestBody AthleteUpdateRequest request,
        @AuthenticationPrincipal Jwt jwt
    ) {
        String externalId = jwt.getSubject();

        return ResponseEntity.ok(athleteServiceImpl.partialUpdate(id, request, externalId));
    }

    @PostMapping(path = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable String id, @RequestPart("avatar") MultipartFile avatarFile) {
        return ResponseEntity.ok(athleteServiceImpl.updateAvatar(id, avatarFile));
    }
}
