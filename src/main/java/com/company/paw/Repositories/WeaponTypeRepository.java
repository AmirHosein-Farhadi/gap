package com.company.paw.Repositories;

import com.company.paw.models.WeaponType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponTypeRepository extends MongoRepository<WeaponType, String> {
}
