package com.coursecrafter.athleteservice.service.mapper;

import com.coursecrafter.athleteservice.domain.AthleteEntity;
import com.coursecrafter.athleteservice.service.dto.AthleteDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AthleteMapper {
    AthleteDto toDto(AthleteEntity entity);
    AthleteEntity toEntity(AthleteDto dto);
}
