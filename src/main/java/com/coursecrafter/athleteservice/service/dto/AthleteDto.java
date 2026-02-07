package com.coursecrafter.athleteservice.service.dto;

import com.coursecrafter.athleteservice.domain.enums.AthleteProfileStatus;
import com.coursecrafter.athleteservice.domain.enums.UnitSystem;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AthleteDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private AthleteProfileStatus status;
    private String avatarUrl;
    private UnitSystem unit;
    private LocalDate dateOfBirth;
    private String experience;
    private String volume;
    private List<String> daysAvailable;
    private String goal;
    private Boolean hasRace;
    private String raceDistance;
    private String raceName;
    private String raceDate;
    private String tracking;
}
