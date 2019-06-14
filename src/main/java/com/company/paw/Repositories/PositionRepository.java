package com.company.paw.Repositories;

import com.company.paw.models.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends MongoRepository<Position, String> {
    Optional<Position> findByTitle(String title);
}
