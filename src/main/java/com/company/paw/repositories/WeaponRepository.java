package com.company.paw.repositories;

import com.company.paw.models.goods.Weapon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends MongoRepository<Weapon, String> {
}
