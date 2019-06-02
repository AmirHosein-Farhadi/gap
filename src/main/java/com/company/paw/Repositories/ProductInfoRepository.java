package com.company.paw.Repositories;

import com.company.paw.models.ProductInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepository extends MongoRepository<ProductInfo, String> {
}
