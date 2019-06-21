package com.company.paw.repositories;

import com.company.paw.models.Request;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends MongoRepository<Request, String> {
    List<Request> findByReportNotNull();
    List<Request> findByReportIsNull();
    List<Request> findByEmployeeId(String id);
    List<Request> findByOrganizationId(String id);
}
