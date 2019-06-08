package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.RecordInput;
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
    public Record addRecord(RecordInput input) {
        return recordRepository.save(addInput(input));
    }

    private Record addInput(RecordInput input) {
        Optional<Employee> employeeOptional = employeeRepository.findById(input.getUserId());
        Optional<Weapon> weaponOptional = weaponRepository.findById(input.getProductId());
        Optional<Plate> plateOptional = plateRepository.findById(input.getProductId());
        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getTime());
        } catch (Exception ignored) {
        }

        Record record = new Record();
        record.setUser(employeeOptional.orElse(null));

        if (weaponOptional.isPresent())
            record.setProduct(weaponOptional.get());
        else
            record.setProduct(plateOptional.orElse(null));

        record.setOrganization(organizationOptional.orElse(null));
        record.setTime(date);
        record.setStatus(input.isStatus());
        record.setReturning(input.isReturning());
        record.setDescription(input.getDescription());

        return record;
    }

    List<Record> recordsIdToRecords(List<String> recordsId) {
        return recordsId.stream()
                .map(record -> recordRepository.findById(record).orElse(null))
                .collect(Collectors.toList());
    }
}
