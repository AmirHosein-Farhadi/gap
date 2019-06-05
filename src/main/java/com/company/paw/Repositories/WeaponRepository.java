package com.company.paw.Repositories;

import com.company.paw.models.Weapon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeaponRepository extends MongoRepository<Weapon, String> {
}
