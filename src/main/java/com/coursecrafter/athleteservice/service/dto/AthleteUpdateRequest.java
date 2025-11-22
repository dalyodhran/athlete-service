package com.coursecrafter.athleteservice.service.dto;

import com.coursecrafter.athleteservice.domain.enums.AthleteProfileStatus;
import com.coursecrafter.athleteservice.domain.enums.UnitSystem;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AthleteUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private AthleteProfileStatus status;
    private String avatarKey;
    private UnitSystem unit;
    private LocalDate dateOfBirth;
}
