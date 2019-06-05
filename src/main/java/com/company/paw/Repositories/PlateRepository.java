package com.company.paw.Repositories;

import com.company.paw.models.Plate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlateRepository extends MongoRepository<Plate, String> {
}
