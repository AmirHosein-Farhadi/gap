package com.company.paw.Repositories;

import com.company.paw.models.WeaponCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponCategoryRepository extends MongoRepository<WeaponCategory, String> {
}
