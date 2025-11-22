package com.coursecrafter.athleteservice.domain;

import com.coursecrafter.athleteservice.domain.enums.AthleteProfileStatus;
import com.coursecrafter.athleteservice.domain.enums.UnitSystem;
import com.coursecrafter.athleteservice.service.dto.JwtUserIdentityDto;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "athletes")
public class AthleteEntity implements java.io.Serializable {

    @Id
    private String id;

    private String externalId;
    private String firstName;
    private String lastName;
    private String email;
    private AthleteProfileStatus status;
    private String avatarKey;
    private LocalDate dateOfBirth;
    private UnitSystem unit;
    private Instant createdAt;
    private Instant updatedAt;

    public static AthleteEntity fromJwtUserIdentity(JwtUserIdentityDto jwt) {
        AthleteEntity athlete = new AthleteEntity();
        athlete.id = UUID.randomUUID().toString();
        athlete.externalId = jwt.getExternalId();
        athlete.firstName = jwt.getFirstName();
        athlete.lastName = jwt.getLastName();
        athlete.email = jwt.getEmail();
        athlete.status = AthleteProfileStatus.DRAFT;
        athlete.createdAt = Instant.now();
        athlete.updatedAt = Instant.now();
        return athlete;
    }
}
