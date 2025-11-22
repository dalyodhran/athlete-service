package com.coursecrafter.athleteservice.repository;

import com.coursecrafter.athleteservice.domain.AthleteEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteRepository extends MongoRepository<AthleteEntity, String> {
    Optional<AthleteEntity> findByExternalId(String externalId);
}
