package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.PlateInput;
import com.company.paw.graphql.InputTypes.ReportInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Plate;
import com.company.paw.repositories.EmployeeRepository;
import com.company.paw.repositories.OrganizationRepository;
import com.company.paw.repositories.PlateRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PlateService {
    private final PlateRepository plateRepository;
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final ConvertService convertService;

    @GraphQLQuery
    public List<Plate> allPlates() {
        return plateRepository.findAll();
    }

    @GraphQLQuery
    public Plate getPlate(String id) {
        return plateRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Plate addPlate(PlateInput privatePlateInput, PlateInput backupPlateInput) {
        Plate privatePlate = convertService.setPlate(new Plate(), privatePlateInput);
        plateRepository.save(privatePlate);

        Plate backupPlate = convertService.setPlate(new Plate(), backupPlateInput);
        plateRepository.save(backupPlate);

        privatePlate.setMappedPlate(backupPlate);
        backupPlate.setMappedPlate(privatePlate);
        plateRepository.save(backupPlate);
        return plateRepository.save(privatePlate);
    }

    @GraphQLMutation
    public Plate editPlate(String privatePlateId, PlateInput privatePlateInput, boolean isChanged, PlateInput newPlate) {
        Plate privatePlate = convertService.setPlate(plateRepository.findById(privatePlateId).get(), privatePlateInput);
        plateRepository.save(privatePlate);

        if (isChanged) {
            Plate backupPlate = convertService.setPlate(new Plate(), newPlate);
            plateRepository.save(backupPlate);
            privatePlate.setMappedPlate(backupPlate);
            backupPlate.setMappedPlate(privatePlate);
            plateRepository.save(backupPlate);
        }

        return plateRepository.save(privatePlate);
    }

    @GraphQLMutation
    public Plate deletePlate(String plateId) {
        Optional<Plate> plateOptional = plateRepository.findById(plateId);
        plateOptional.ifPresent(plateRepository::delete);
        return plateOptional.orElse(null);
    }

    @GraphQLMutation
    public Plate recievePlate(ReportInput input) {
        Plate plate = plateRepository.findById(input.getProductId()).get();
        Employee employee = employeeRepository.findById(input.getEmployeeId()).get();

        plate.getMappedPlate().setPlateStatus(2);
        plate.getMappedPlate().setCurrentUser(employee);
        plateRepository.save(plate.getMappedPlate());

        return convertService.plateInUse(plate, employee, input);
    }

    @GraphQLMutation
    public Plate returnPlate(String plateId, boolean returnStatus, String returnDate, String returnDescription) {
        return (Plate) convertService.returnProduct(plateId, returnStatus, returnDate, returnDescription);
    }
}
