package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.graphql.InputTypes.ProductReturnInput;
import com.company.paw.models.*;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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

    @GraphQLMutation
    public Plate editPlate(String id, ProductInput input) {
        return plateRepository.save(editInput(id, input));
    }

    @GraphQLMutation
    public Plate usePlate(ProductReturnInput input) {
        Plate plate = plateRepository.findById(input.getProductId()).get();
        Employee employee = employeeRepository.findById(input.getEmployeeId()).get();

        Record record = new Record();
        record.setUser(employee);
        record.setOrganization(plate.getOrganization());
        record.setProduct(plate);
        record.setReturning(false);
        record.setStatus(input.isStatus());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getDate());
        } catch (Exception ignored) {
        }
        record.setTime(date);
        record.setDescription(input.getDescription());
        recordRepository.save(record);

        plate.getRecords().add(record);
        plate.setCurrentUser(employee);
        //todo add status to Product
        return plateRepository.save(plate);
    }

    @GraphQLMutation
    public Plate returnPlate(ProductReturnInput input) {
        Plate plate = plateRepository.findById(input.getProductId()).get();
        Employee employee = employeeRepository.findById(input.getEmployeeId()).get();

        Record record = new Record();
        record.setUser(employee);
        record.setOrganization(plate.getOrganization());
        record.setProduct(plate);
        record.setReturning(true);
        record.setStatus(input.isStatus());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getDate());
        } catch (Exception ignored) {
        }
        record.setTime(date);
        record.setDescription(input.getDescription());
        recordRepository.save(record);

        plate.getRecords().add(record);
        plate.setCurrentUser(null);
        return plateRepository.save(plate);
    }

    private Plate addInput(ProductInput input) {
        Optional<Request> requestOptional = requestRepository.findById(input.getRequestId());
        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());
        Optional<Employee> employeeOptional = employeeRepository.findById(input.getCurrentUserId());
        Optional<Plate> mappedPlateOptional = plateRepository.findById(input.getMappedPlateId());

        Plate plate = new Plate();
        plate.setSerial(input.getSerial());
        plate.setRequest(requestOptional.orElse(null));
        plate.setOrganization(organizationOptional.orElse(null));
        plate.setCurrentUser(employeeOptional.orElse(null));
        if (input.getRecordsId() != null && !input.getRecordsId().isEmpty())
            plate.setRecords(recordsService.recordsIdToRecords(input.getRecordsId()));
        else
            plate.setRecords(Collections.emptyList());
        if (input.getImagesId() != null && !input.getImagesId().isEmpty())
            plate.setImages(imageService.imagesIdToImages(input.getImagesId()));
        else
            plate.setImages(Collections.emptyList());
        plate.setMappedPlate(mappedPlateOptional.orElse(null));
        return plate;
    }

    private Plate editInput(String id, ProductInput input) {
        Optional<Request> requestOptional = requestRepository.findById(input.getRequestId());
        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());
        Optional<Employee> employeeOptional = employeeRepository.findById(input.getCurrentUserId());
        Optional<Plate> plateOptional = plateRepository.findById(input.getMappedPlateId());

        Plate plate = plateRepository.findById(id).get();
        if (input.getSerial() != null)
            plate.setSerial(input.getSerial());
        if (input.getRecordsId() != null)
            plate.setRequest(requestOptional.orElse(null));
        if (input.getOrganizationId() != null)
            plate.setOrganization(organizationOptional.orElse(null));
        if (input.getCurrentUserId() != null)
            plate.setCurrentUser(employeeOptional.orElse(null));
        if (input.getRecordsId() != null)
            plate.setRecords(recordsService.recordsIdToRecords(input.getRecordsId()));
        if (input.getImagesId() != null)
            plate.setImages(imageService.imagesIdToImages(input.getImagesId()));
        if (input.getMappedPlateId() != null)
            plate.setMappedPlate(plateOptional.orElse(null));
        return plate;
    }
}
