package com.company.paw.repositories;

import com.company.paw.models.goods.WeaponType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeaponTypeRepository extends MongoRepository<WeaponType, String> {
    Optional<WeaponType> findByName(String name);
}
