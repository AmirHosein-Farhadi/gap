package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import com.company.paw.models.Plate;
import com.company.paw.models.Request;
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
    private final RequestRepository requestRepository;
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final RecordRepository recordRepository;
    private final ImageService imageService;
    private final RecordsService recordsService;

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

    private Plate addInput(ProductInput input) {
        Optional<Request> requestOptional = requestRepository.findById(input.getRequestId());
        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());
        Optional<Employee> employeeOptional = employeeRepository.findById(input.getCurrentUserId());
        Optional<Plate> plateOptional = plateRepository.findById(input.getMappedPlateId());

        Plate plate = new Plate();
        plate.setSerial(input.getSerial());
        plate.setRequest(requestOptional.orElse(null));
        plate.setOrganization(organizationOptional.orElse(null));
        plate.setCurrentUser(employeeOptional.orElse(null));
        plate.setRecords(recordsService.recordsIdToRecords(input.getRecordsId()));
        plate.setImages(imageService.imagesIdToImages(input.getImagesId()));
        plate.setPrivate(input.isPrivate());
        plate.setMappedPlate(plateOptional.orElse(null));
        return plate;
    }
}
