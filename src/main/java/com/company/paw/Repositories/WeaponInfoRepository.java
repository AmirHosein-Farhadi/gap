package com.company.paw.Repositories;

import com.company.paw.models.WeaponInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponInfoRepository extends MongoRepository<WeaponInfo, String> {
}
