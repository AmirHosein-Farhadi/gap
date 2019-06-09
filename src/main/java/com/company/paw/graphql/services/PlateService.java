package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.models.*;
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
    private final ReportsService reportsService;
    private final EmployeeService employeeService;


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

//    @GraphQLMutation
//    public Plate editPlate(String id, ProductInput input) {
//        return plateRepository.save(editInput(id, input));
//    }

//    @GraphQLMutation
//    public Plate usePlate(ProductReturnInput input) {
//        Plate plate = plateRepository.findById(input.getProductId()).get();
//        Employee employee = employeeRepository.findById(input.getEmployeeId()).get();
//
//        Report report = new Report();
//        report.setUser(employee);
//        report.setOrganization(plate.getOrganization());
//        report.setProduct(plate);
//        report.setReturning(false);
//        report.setStatus(input.isStatus());
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getDate());
//        } catch (Exception ignored) {
//        }
//        report.setTime(date);
//        report.setDescription(input.getDescription());
//        recordRepository.save(report);
//
//        plate.getReports().add(report);
//        plate.setCurrentUser(employee);
//        return plateRepository.save(plate);
//    }

//    @GraphQLMutation
//    public Plate returnPlate(ProductReturnInput input) {
//        Plate plate = plateRepository.findById(input.getProductId()).get();
//        Employee employee = employeeRepository.findById(input.getEmployeeId()).get();
//
//        Report report = new Report();
//        report.setUser(employee);
//        report.setOrganization(plate.getOrganization());
//        report.setProduct(plate);
//        report.setReturning(true);
//        report.setStatus(input.isStatus());
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getDate());
//        } catch (Exception ignored) {
//        }
//        report.setTime(date);
//        report.setDescription(input.getDescription());
//        recordRepository.save(report);
//
//        plate.getReports().add(report);
//        plate.setCurrentUser(null);
//        return plateRepository.save(plate);
//    }

    private Plate addInput(ProductInput input) {
        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());
        Optional<Image> imageOptional = imageRepository.findById(input.getImageId());
        Optional<Plate> mappedPlateOptional;

        Plate plate = new Plate();
        if (input.getMappedPlateId() != null) {
            mappedPlateOptional = plateRepository.findById(input.getMappedPlateId());
            plate.setMappedPlate(mappedPlateOptional.orElse(null));
        } else {
            plate.setMappedPlate(null);
        }

        if (input.getCurrentUsersId() != null && !input.getCurrentUsersId().isEmpty())
            plate.setCurrentUsers(employeeService.employeesIdToEmployees(input.getCurrentUsersId()));
        else
            plate.setCurrentUsers(Collections.emptyList());

        plate.setSerial(input.getSerial());

        plate.setImage(imageOptional.orElse(null));

        plate.setOrganization(organizationOptional.orElse(null));
        if (input.getReportsId() != null && !input.getReportsId().isEmpty()) {
            plate.setReports(reportsService.recordsIdToRecords(input.getReportsId()));
        } else {
            plate.setReports(Collections.emptyList());
        }

        return plate;
    }

//    private Plate editInput(String id, ProductInput input) {
//        Optional<Request> requestOptional = requestRepository.findById(input.getRequestId());
//        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());
//        Optional<Employee> employeeOptional = employeeRepository.findById(input.getCurrentUserId());
//        Optional<Plate> plateOptional = plateRepository.findById(input.getMappedPlateId());
//
//        Plate plate = plateRepository.findById(id).get();
//        if (input.getSerial() != null)
//            plate.setSerial(input.getSerial());
//        if (input.getReportsId() != null)
//            plate.setRequest(requestOptional.orElse(null));
//        if (input.getOrganizationId() != null)
//            plate.setOrganization(organizationOptional.orElse(null));
//        if (input.getCurrentUserId() != null)
//            plate.setCurrentUser(employeeOptional.orElse(null));
//        if (input.getReportsId() != null)
//            plate.setReports(recordsService.recordsIdToRecords(input.getReportsId()));
//        if (input.getImagesId() != null)
//            plate.setImages(imageService.imagesIdToImages(input.getImagesId()));
//        if (input.getMappedPlateId() != null)
//            plate.setMappedPlate(plateOptional.orElse(null));
//        return plate;
//    }
}
