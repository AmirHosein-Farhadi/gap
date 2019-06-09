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
    public List<Report> allRecords() {
        return reportRepository.findAll();
    }

    @GraphQLQuery
    public Report getRecord(String id) {
        return reportRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Report addRecord(ReportInput input) {
        return reportRepository.save(addInput(input));
    }

    private Report addInput(ReportInput input) {
        Optional<Employee> employeeOptional = employeeRepository.findById(input.getUserId());
        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());

        Optional<Weapon> weaponOptional = weaponRepository.findById(input.getProductId());
        Optional<Plate> plateOptional = plateRepository.findById(input.getProductId());

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getBorrowTime());
        } catch (Exception ignored) {
        }

        Report report = new Report();
        report.setUser(employeeOptional.orElse(null));

        if (weaponOptional.isPresent())
            report.setProduct(weaponOptional.get());
        else
            report.setProduct(plateOptional.orElse(null));

        report.setRequest(requestRepository.findById(input.getRequestId()).orElse(null));
        report.setOrganization(organizationOptional.orElse(null));
        report.setBorrowTime(date);
        report.setBorrowStatus(input.isBorrowStatus());
        report.setDescription(input.getDescription());

        return report;
    }

    List<Report> recordsIdToRecords(List<String> recordsId) {
        return recordsId.stream()
                .map(record -> reportRepository.findById(record).orElse(null))
                .collect(Collectors.toList());
    }
}
