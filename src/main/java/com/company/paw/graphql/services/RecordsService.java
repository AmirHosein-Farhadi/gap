package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.models.Record;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecordsService {
    private final RecordRepository recordRepository;
    private final EmployeeRepository employeeRepository;
    private final WeaponRepository weaponRepository;
    private final PlateRepository plateRepository;
    private final OrganizationRepository organizationRepository;

    @GraphQLQuery
    public List<Record> allRecords() {
        return recordRepository.findAll();
    }

    @GraphQLQuery
    public Record getRecord(String id) {
        return recordRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Record addRecord(String id) {

        return recordRepository.findById(id).orElse(null);
    }

    public List<Record> recordsIdToRecords(List<String> recordsId) {
        return recordsId.stream()
                .map(record -> recordRepository.findById(record).orElse(null))
                .collect(Collectors.toList());
    }
}
