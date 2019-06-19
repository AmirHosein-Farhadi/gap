package com.company.paw.graphql.services;

import com.company.paw.models.Report;
import com.company.paw.repositories.ReportRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportsService {
    private final ReportRepository reportRepository;

    @GraphQLQuery
    public List<Report> allReports() {
        return reportRepository.findAll();
    }

    @GraphQLQuery
    public Report getReport(String id) {
        return reportRepository.findById(id).orElse(null);
    }

    @GraphQLQuery
    public List<Report> productReports(String id){
        return reportRepository.findByProductIdOrderByIdDesc(id);
    }

    List<Report> ReportsIdToReports(List<String> ReportsId) {
        return ReportsId.stream()
                .map(report -> reportRepository.findById(report).orElse(null))
                .collect(Collectors.toList());
    }
}
