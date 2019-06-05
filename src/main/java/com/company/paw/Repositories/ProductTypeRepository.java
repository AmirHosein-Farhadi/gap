package com.company.paw.Repositories;

import com.company.paw.models.ProductType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends MongoRepository<ProductType, String> {
}
