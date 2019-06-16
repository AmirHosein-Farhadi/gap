package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.ReportInput;
import com.company.paw.models.Report;
import com.company.paw.repositories.ReportRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportsService {
    private final ReportRepository reportRepository;
    private final ConvertService convertService;

    @GraphQLQuery
    public List<Report> allReports() {
        return reportRepository.findAll();
    }

    @GraphQLQuery
    public Report getReport(String id) {
        return reportRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Report addReport(ReportInput input) {
        return reportRepository.save(convertService.setReport(new Report(), input));
    }

    @GraphQLMutation
    public Report editReport(String reportId, ReportInput input) {
        return reportRepository.save(convertService.setReport(reportRepository.findById(reportId).get(), input));
    }

    @GraphQLMutation
    public Report deleteReport(String reportId) {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        reportOptional.ifPresent(reportRepository::delete);
        return reportOptional.orElse(null);
    }

    List<Report> ReportsIdToReports(List<String> ReportsId) {
        return ReportsId.stream()
                .map(report -> reportRepository.findById(report).orElse(null))
                .collect(Collectors.toList());
    }
}
