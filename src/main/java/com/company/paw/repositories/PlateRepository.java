package com.company.paw.repositories;

import com.company.paw.models.Plate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlateRepository extends MongoRepository<Plate, String> {
    List<Plate> findByOrganizationId(String id);
}
