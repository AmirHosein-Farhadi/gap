package com.company.paw.repositories;

import com.company.paw.models.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    List<Report> findByProductIdOrderByIdDesc(String id);
    List<Report> findByEmployeeIdOrderByIdDesc(String id);
    List<Report> findByOrganizationIdOrderByIdDesc(String id);
}
