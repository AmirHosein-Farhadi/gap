package com.company.paw.Repositories;

import com.company.paw.models.Documents;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends MongoRepository<Documents, String> {
}
