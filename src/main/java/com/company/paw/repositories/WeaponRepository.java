package com.company.paw.repositories;

import com.company.paw.models.Weapon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeaponRepository extends MongoRepository<Weapon, String> {
    List<Weapon> findByOrganizationId(String id);
}
