package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.models.*;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlateService {
    private final PlateRepository plateRepository;
    private final OrganizationRepository organizationRepository;
    private final ImageRepository imageRepository;
    private final EmployeeRepository employeeRepository;


    @GraphQLQuery
    public List<Plate> allPlates() {
        return plateRepository.findAll();
    }

    @GraphQLQuery
    public Plate getPlate(String id) {
        return plateRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Plate addPlate(ProductInput input) {
        return plateRepository.save(addInput(input));
    }

    @GraphQLMutation
    public Plate editPlate(String plateId, ProductInput input) {
        return plateRepository.save(editInput(plateId, input));
    }

    @GraphQLMutation
    public Plate deletePlate(String plateId) {
        Optional<Plate> plateOptional = plateRepository.findById(plateId);
        plateOptional.ifPresent(plateRepository::delete);
        return plateOptional.orElse(null);
    }

    @GraphQLMutation
    public Plate attachMappedPlate(String plateId, String mappedPlateId) {
        Plate plate = plateRepository.findById(plateId).get();
        Plate mappedPlate = plateRepository.findById(mappedPlateId).get();
        plate.setMappedPlate(mappedPlate);
        mappedPlate.setMappedPlate(plate);
        plateRepository.save(plate);
        return plateRepository.save(mappedPlate);
    }

    @GraphQLMutation
    public Employee setPlateUser(String plateId, String employeeId) {
        Plate plate = plateRepository.findById(plateId).get();
        Employee employee = employeeRepository.findById(employeeId).get();
        plate.setCurrentUsers(Collections.singletonList(employee));
        List<Plate> plates = employee.getPlates();
        plates.add(plate);
        employee.setPlates(plates);
        plateRepository.save(plate);
        return employeeRepository.save(employee);
    }

    private Plate addInput(ProductInput input) {
        Plate plate = new Plate();
        plate.setSerial(input.getSerial());
        plate.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        plate.setImage(imageRepository.findById(input.getImageId()).get());
        plate.setMinority(imageRepository.findById(input.getMinorityId()).get());
        plate.setPrivate(input.isPrivate());
        plate.setCurrentUsers(Collections.emptyList());
        plate.setReports(Collections.emptyList());
        return plate;
    }

    private Plate editInput(String plateId, ProductInput input) {
        Plate plate = plateRepository.findById(plateId).get();
        if (input.getSerial() != null)
            plate.setSerial(input.getSerial());
        if (input.getOrganizationId() != null)
            plate.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        if (input.getImageId() != null)
            plate.setImage(imageRepository.findById(input.getImageId()).get());
        if (input.getMinorityId() != null)
            plate.setMinority(imageRepository.findById(input.getMinorityId()).get());
        plate.setPrivate(input.isPrivate());
        return plate;
    }
}
