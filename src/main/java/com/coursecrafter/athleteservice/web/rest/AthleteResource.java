package com.coursecrafter.athleteservice.web.rest;

import com.coursecrafter.athleteservice.domain.AthleteEntity;
import com.coursecrafter.athleteservice.repository.AthleteRepository;
import java.net.URI;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AthleteResource {

    private final Logger log = LoggerFactory.getLogger(AthleteResource.class);
    private final AthleteRepository athleteRepository;

    public AthleteResource(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    @GetMapping("/athletes/me")
    public ResponseEntity<AthleteEntity> getOrCreateMe(@AuthenticationPrincipal Jwt jwt) {
        // this is the Keycloak user id (the "sub" claim)
        String identityId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String givenName = jwt.getClaimAsString("given_name");
        String familyName = jwt.getClaimAsString("family_name");

        log.debug("REST request to get/create Athlete for identityId: {}", identityId);

        Optional<AthleteEntity> existing = athleteRepository.findByIdentityId(identityId);
        if (existing.isPresent()) {
            return ResponseEntity.ok(existing.get());
        }

        // create new athlete if not found
        AthleteEntity athlete = new AthleteEntity();
        athlete.setIdentityId(identityId);
        athlete.setEmail(email);
        athlete.setFirstName(givenName);
        athlete.setLastName(familyName);

        AthleteEntity saved = athleteRepository.save(athlete);

        return ResponseEntity.created(URI.create("/api/athletes/" + saved.getId())).body(saved);
    }
}
