package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.PlateInput;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.graphql.InputTypes.ReportInput;
import com.company.paw.models.*;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import io.leangen.graphql.annotations.GraphQLMutation;
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
    private final OrganizationRepository organizationRepository;
    private final ImageRepository imageRepository;
    private final EmployeeRepository employeeRepository;
    private final RequestRepository requestRepository;
    private final ReportRepository reportRepository;

    @GraphQLQuery
    public List<Plate> allPlates() {
        return plateRepository.findAll();
    }

    @GraphQLQuery
    public Plate getPlate(String id) {
        return plateRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Plate addPlate(PlateInput input) {

        Plate privatePlate = new Plate();
        privatePlate.setSerial(input.getPrivatePlateSerial());
        privatePlate.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        privatePlate.setMinority(imageRepository.findById(input.getMinorityImage()).orElse(null));
        privatePlate.setPlateStatus(input.getPrivatePlateStatus());
        privatePlate.setReports(Collections.emptyList());
        plateRepository.save(privatePlate);

        Plate backupPlate = new Plate();
        backupPlate.setSerial(input.getBackupPlateSerial());
        backupPlate.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        backupPlate.setPlateStatus(input.getBackupPlateStatus());
        backupPlate.setReports(Collections.emptyList());
        backupPlate.setMappedPlate(privatePlate);
        plateRepository.save(backupPlate);

        privatePlate.setMappedPlate(backupPlate);
        return plateRepository.save(privatePlate);
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
    public Plate recievePlate(ReportInput input) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getBorrowTime());
        } catch (Exception ignored) {
        }
        Plate plate = plateRepository.findById(input.getProductId()).orElse(null);
        Employee employee = employeeRepository.findById(input.getEmployeeId()).orElse(null);

        Report report = new Report();
        report.setEmployee(employee);
        report.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        report.setProduct(plateRepository.findById(input.getProductId()).orElse(null));
        report.setRequest(requestRepository.findById(input.getRequestId()).orElse(null));
        report.setBorrowTime(date);
        report.setBorrowStatus(input.isBorrowStatus());
        report.setBorrowDescription(input.getBorrowDescription());
        report.setAcceptImage(imageRepository.findById(input.getAcceptImageId()).orElse(null));
        report.setReciteImage(imageRepository.findById(input.getReciteImageId()).orElse(null));
        report.setInformationLetter(imageRepository.findById(input.getInformationLetterId()).orElse(null));
        report.setReciteImage(imageRepository.findById(input.getReciteImageId()).orElse(null));
        reportRepository.save(report);

        plate.setCurrentUser(employee);
        List<Report> reports = plate.getReports();
        reports.add(report);
        plate.setReports(reports);
        return plateRepository.save(plate);
    }


    @GraphQLMutation
    public Plate returnPlate(String plateId, boolean returnStatus, String returnDate, String returnDescription) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(returnDate);
        } catch (Exception ignored) {
        }
        Plate plate = plateRepository.findById(plateId).get();
        plate.setCurrentUser(null);
        plateRepository.save(plate);

        List<Report> reports = plate.getReports();
        Report report = reports.get(reports.size() - 1);
        report.setReturnStatus(returnStatus);
        report.setReturnDescription(returnDescription);
        report.setReturnTime(date);
        reportRepository.save(report);

        return plate;
    }


    @GraphQLMutation
    public Plate changeMappedPlate(String plateSerial, String plateId) {
        Plate plate = plateRepository.findById(plateId).get();

        Plate mappedPlate = plate.getMappedPlate();
        mappedPlate.setSerial(plateSerial);
        return plateRepository.save(mappedPlate);
    }

    @GraphQLMutation
    public Employee setPlateUser(String plateId, String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();

        Plate plate = plateRepository.findById(plateId).get();
        plate.setCurrentUser(employee);
        plateRepository.save(plate);

        List<Plate> plates = employee.getPlates();
        plates.add(plate);
        employee.setPlates(plates);
        return employeeRepository.save(employee);
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
