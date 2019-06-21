package com.company.paw.repositories;

import com.company.paw.models.Equipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends MongoRepository<Equipment, String> {
    List<Equipment> findByType(int type);

    List<Equipment> findByOrganizationId(String id);
}
