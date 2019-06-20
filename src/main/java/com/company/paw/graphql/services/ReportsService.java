package com.company.paw.graphql.services;

import com.company.paw.models.Equipment;
import com.company.paw.models.Report;
import com.company.paw.repositories.ReportRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Report> productReports(String id) {
        return reportRepository.findByProductIdOrderByIdDesc(id);
    }

    @GraphQLQuery
    public Map<String, List<Report>> employeeReports(String id) {
        List<Report> reports = reportRepository.findByEmployeeId(id);
        Map<String, List<Report>> employeeMap = new HashMap<>();
        List<Report> weapons = new ArrayList<>();
        List<Report> plates = new ArrayList<>();
        List<Report> sprays = new ArrayList<>();
        List<Report> shockers = new ArrayList<>();
        List<Report> talkies = new ArrayList<>();

        for (Report report : reports) {
            if (report.getProduct().getClass().getName().contains("Weapon"))
                weapons.add(report);
            else if (report.getProduct().getClass().getName().contains("Plate"))
                plates.add(report);
            else {
                if (((Equipment) report.getProduct()).getType() == 1)
                    sprays.add(report);
                else if (((Equipment) report.getProduct()).getType() == 2)
                    shockers.add(report);
                else if (((Equipment) report.getProduct()).getType() == 3)
                    talkies.add(report);
            }
        }
        employeeMap.put("Weapons", weapons);
        employeeMap.put("Plates", plates);
        employeeMap.put("Sprays", sprays);
        employeeMap.put("Shockers", shockers);
        employeeMap.put("Talkies", talkies);
        return employeeMap;
    }
}
