package com.company.paw.repositories;

import com.company.paw.models.WeaponName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeaponNameRepository extends MongoRepository<WeaponName, String> {
    Optional<WeaponName> findByName(String name);
}
