package com.company.paw.Repositories;

import com.company.paw.models.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends MongoRepository<State, String> {
    Optional<State> findByName(String name);
}
