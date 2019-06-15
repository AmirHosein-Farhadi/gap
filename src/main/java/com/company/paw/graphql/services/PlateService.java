package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.PlateInput;
import com.company.paw.graphql.InputTypes.ReportInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import com.company.paw.models.Plate;
import com.company.paw.models.Report;
import com.company.paw.repositories.*;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlateService {
    private final PlateRepository plateRepository;
    private final OrganizationRepository organizationRepository;
    private final ImageRepository imageRepository;
    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;
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
    public Plate editPlate(String plateId, PlateInput input, PlateInput newPlate) {
        return plateRepository.save(editInput(plateId, input, newPlate));
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
        Organization organization = organizationRepository.findById(input.getOrganizationId()).get();

        plate.getMappedPlate().setPlateStatus(2);
        plate.getMappedPlate().setCurrentUser(employee);
        plate.getMappedPlate().setOrganization(organization);

        return convertService.plateInUse(plate,employee,organization,input);
    }

    @GraphQLMutation
    public Plate returnPlate(String plateId, boolean returnStatus, String returnDate, String returnDescription) {
        return (Plate)convertService.returnProduct(plateId, returnStatus,returnDate,returnDescription);
    }

    private Plate editInput(String plateId, PlateInput input, PlateInput newPlate) {
        Plate plate = convertService.setPlate(plateRepository.findById(plateId).get(), input);
        if (plate.isPrivate() && newPlate != null) {
            Plate newMappedPlate = convertService.setPlate(new Plate(), newPlate);
            Plate oldMappedPlate = plate.getMappedPlate();
            newMappedPlate.setMappedPlate(plate);
            plateRepository.save(newMappedPlate);

            oldMappedPlate.setMappedPlate(null);
            plateRepository.save(oldMappedPlate);

            plate.setMappedPlate(newMappedPlate);
            plateRepository.save(plate);
        }
        return plate;
    }
}
