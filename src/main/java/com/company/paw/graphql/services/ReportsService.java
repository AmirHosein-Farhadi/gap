package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.ReportInput;
import com.company.paw.models.*;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportsService {
    private final RequestRepository requestRepository;
    private final ReportRepository reportRepository;
    private final EmployeeRepository employeeRepository;
    private final WeaponRepository weaponRepository;
    private final PlateRepository plateRepository;
    private final OrganizationRepository organizationRepository;

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
        Report report = addInput(input);
        reportRepository.save(report);

        Request request = requestRepository.findById(report.getRequest().getId()).get();
        request.setReport(report);
        requestRepository.save(request);
        return report;
    }

    @GraphQLMutation
    public Report editReport(String reportId, ReportInput input) {
        return reportRepository.save(editInput(reportId, input));
    }

    @GraphQLMutation
    public Report deleteReport(String reportId) {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        reportOptional.ifPresent(reportRepository::delete);
        return reportOptional.orElse(null);
    }

    private Report addInput(ReportInput input) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getBorrowTime());
        } catch (Exception ignored) {
        }
        Optional<Weapon> weaponOptional = weaponRepository.findById(input.getProductId());
        Optional<Plate> plateOptional = plateRepository.findById(input.getProductId());

        Report report = new Report();
        report.setUser(employeeRepository.findById(input.getUserId()).get());
        report.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        report.setRequest(requestRepository.findById(input.getRequestId()).orElse(null));
        report.setBorrowTime(date);
        report.setBorrowStatus(input.isBorrowStatus());
        report.setDescription(input.getDescription());

        if (weaponOptional.isPresent())
            report.setProduct(weaponOptional.get());
        else
            report.setProduct(plateOptional.orElse(null));
        return report;
    }

    private Report editInput(String reportId, ReportInput input) {
        Report report = reportRepository.findById(reportId).get();
        if (input.getBorrowTime() != null) {
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getBorrowTime());
            } catch (Exception ignored) {
            }
            report.setBorrowTime(date);
        }
        Optional<Weapon> weaponOptional = weaponRepository.findById(input.getProductId());
        Optional<Plate> plateOptional = plateRepository.findById(input.getProductId());

        if (input.getUserId() != null)
            report.setUser(employeeRepository.findById(input.getUserId()).get());
        if (input.getOrganizationId() != null)
            report.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        if (input.getRequestId() != null)
            report.setRequest(requestRepository.findById(input.getRequestId()).orElse(null));
        if (input.getDescription() != null)
            report.setDescription(input.getDescription());
        report.setBorrowStatus(input.isBorrowStatus());

        if (weaponOptional.isPresent())
            report.setProduct(weaponOptional.get());
        else
            report.setProduct(plateOptional.orElse(null));
        return report;
    }

    List<Report> ReportsIdToReports(List<String> ReportsId) {
        return ReportsId.stream()
                .map(report -> reportRepository.findById(report).orElse(null))
                .collect(Collectors.toList());
    }
}
