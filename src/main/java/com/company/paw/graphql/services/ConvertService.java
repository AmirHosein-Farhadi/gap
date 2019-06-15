package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.EmployeeInput;
import com.company.paw.graphql.InputTypes.OrganizationInput;
import com.company.paw.graphql.InputTypes.PlateInput;
import com.company.paw.graphql.InputTypes.ReportInput;
import com.company.paw.models.*;
import com.company.paw.models.audits.Product;
import com.company.paw.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
class ConvertService {
    private final PositionRepository positionRepository;
    private final OrganizationRepository organizationRepository;
    private final EquipmentRepository equipmentRepository;
    private final ImageRepository imageRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;
    private final PlateRepository plateRepository;
    private final WeaponRepository weaponRepository;
    private final RequestRepository requestRepository;
    private final ImageService imageService;

    Employee setEmployee(Employee employee, EmployeeInput input) {
        Date birthDate = null;
        Date employeeExpirationDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy/MM/dd").parse(input.getBirthDate());
            employeeExpirationDate = new SimpleDateFormat("yyyy/MM/dd").parse(input.getEmployeeCardExpirationDate());
        } catch (Exception ignored) {
        }

        if (input.getFullName() != null)
            employee.setFullName(input.getFullName());

        if (birthDate != null)
            employee.setBirthDate(birthDate);

        if (input.getAddress() != null)
            employee.setAddress(input.getAddress());

        if (input.getPhoneNumber() != null)
            employee.setPhoneNumber(input.getPhoneNumber());

        if (input.getNationalId() != null)
            employee.setNationalId(input.getNationalId());

        if (employeeExpirationDate != null)
            employee.setEmployeeCardExpirationDate(employeeExpirationDate);

        if (input.getEmployeeCardNumber() != null)
            employee.setEmployeeCardNumber(input.getEmployeeCardNumber());


        if (input.getEmployeeCardImageId() != null)
            employee.setEmployeeCardImage(imageRepository.findById(input.getEmployeeCardImageId()).orElse(null));

        if (input.getPositionId() != null)
            employee.setPosition(positionRepository.findById(input.getPositionId()).orElse(null));

        if (input.getOrganizationId() != null)
            employee.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));

        if (input.getImageId() != null)
            employee.setImage(imageRepository.findById(input.getImageId()).orElse(null));

        if (input.getSprayId() != null)
            employee.setSpray(equipmentRepository.findById(input.getSprayId()).orElse(null));

        if (input.getShockerId() != null)
            employee.setShocker(equipmentRepository.findById(input.getShockerId()).orElse(null));

        if (input.getTalkieId() != null)
            employee.setTalkie(equipmentRepository.findById(input.getTalkieId()).orElse(null));

        return employee;
    }

    Organization setOrganization(Organization organization, OrganizationInput input) {
        if (input.getName() != null)
            organization.setName(input.getName());
        if (input.getAddress() != null)
            organization.setAddress(input.getAddress());
        if (input.getCityId() != null)
            organization.setCity(cityRepository.findById(input.getCityId()).orElse(null));
        if (input.getStateId() != null)
            organization.setState(stateRepository.findById(input.getStateId()).orElse(null));
        organization.setBulletsQuantity(input.getBulletsQuantity());
        return organization;
    }

    Plate setPlate(Plate plate, PlateInput input) {
        if (input.getSerial() != null)
            plate.setSerial(input.getSerial());
        if (input.getOrganizationId() != null)
            plate.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        if (input.getMinorityImagesId() != null && !input.getMinorityImagesId().isEmpty())
            plate.setMinority(imageService.imagesIdToImages(input.getMinorityImagesId()));
        plate.setPlateStatus(input.getPlateStatus());
        plate.setPrivate(input.isPrivate());
        plate.setStatus(input.isStatus());
        return plate;
    }

    Plate plateInUse(Plate plate, Employee employee, Organization organization, ReportInput input) {
        Report report = setReport(new Report(), input);
        plate.setPlateStatus(2);
        plate.setCurrentUser(employee);
        plate.setOrganization(organization);

        List<Plate> plates = employee.getPlates();
        plates.add(plate);
        employee.setPlates(plates);

        plates = organization.getPlates();
        plates.add(plate);
        organization.setPlates(plates);

        List<Report> reports = plate.getReports();
        reports.add(report);
        plate.setReports(reports);

        reports = employee.getReports();
        reports.add(report);
        employee.setReports(reports);

        reports = organization.getReports();
        reports.add(report);
        organization.setReports(reports);

        organizationRepository.save(organization);
        employeeRepository.save(employee);
        return plateRepository.save(plate);
    }

    Product returnProduct(String productId, boolean returnStatus, String returnDate, String returnDescription) {
        Optional<Plate> plateOptional = plateRepository.findById(productId);
        Optional<Weapon> weaponOptional = weaponRepository.findById(productId);

        Product product = null;
        if (plateOptional.isPresent())
            product = plateOptional.get();
        else if (weaponOptional.isPresent())
            product = weaponOptional.get();
        assert product != null;

        Employee employee = product.getCurrentUser();
        Organization organization = product.getOrganization();
        product.setCurrentUser(null);
        product.setOrganization(null);
        List<Weapon> weapons;
        List<Plate> plates;
        List<Equipment> equipments;

        String type = product.getClass().getName();
        if (type.contains("Weapon")) {
            weapons = employee.getWeapons();
            weapons.remove(product);
            employee.setWeapons(weapons);

            weapons = organization.getWeapons();
            weapons.remove(product);
            organization.setWeapons(weapons);
        } else if (type.contains("Plate")) {
            plates = employee.getPlates();
            plates.remove(product);
            employee.setPlates(plates);

            plates = organization.getPlates();
            plates.remove(product);
            organization.setPlates(plates);
        } else if (type.contains("Equipment")) {
            Equipment equipment = (Equipment) product;
            int equipmentType = equipment.getType();
            if (equipmentType == 1)
                employee.setSpray(null);
            else if (equipmentType == 2)
                employee.setShocker(null);
            else if (equipmentType == 3)
                employee.setTalkie(null);

            equipments = organization.getEquipments();
            equipments.remove(product);
            organization.setEquipments(equipments);
        }

        employeeRepository.save(employee);
        organizationRepository.save(organization);

        List<Report> reports = product.getReports();
        Report report = reports.get(reports.size() - 1);
        report.setReturnStatus(returnStatus);
        report.setReturnTime(stringToDate(returnDate));
        report.setReturnDescription(returnDescription);
        reportRepository.save(report);
        return product;
    }

    Report setReport(Report report, ReportInput input) {

        Optional<Plate> plateOptional = plateRepository.findById(input.getProductId());
        Optional<Weapon> weaponOptional = weaponRepository.findById(input.getProductId());
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(input.getProductId());

        if (plateOptional.isPresent())
            report.setProduct(plateOptional.get());
        else if (weaponOptional.isPresent())
            report.setProduct(weaponOptional.get());
        else equipmentOptional.ifPresent(report::setProduct);

        report.setEmployee(employeeRepository.findById(input.getEmployeeId()).orElse(null));
        report.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        report.setBorrowTime(stringToDate(input.getBorrowTime()));
        report.setRequest(requestRepository.findById(input.getRequestId()).orElse(null));
        report.setBorrowStatus(input.isBorrowStatus());
        report.setBorrowDescription(input.getBorrowDescription());
        report.setInformationLetter(imageRepository.findById(input.getInformationLetterId()).orElse(null));
        report.setArmyLetter(imageRepository.findById(input.getArmyLetterId()).orElse(null));
        report.setAcceptImage(imageRepository.findById(input.getAcceptImageId()).orElse(null));
        report.setReciteImage(imageRepository.findById(input.getReciteImageId()).orElse(null));
        return reportRepository.save(report);
    }

    public Date stringToDate(String stringDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(stringDate);
        } catch (Exception ignored) {
        }
        return date;
    }
}
