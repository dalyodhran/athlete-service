package com.coursecrafter.athleteservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserIdentityDto {

    private String externalId;
    private String firstName;
    private String lastName;
    private String email;
}
