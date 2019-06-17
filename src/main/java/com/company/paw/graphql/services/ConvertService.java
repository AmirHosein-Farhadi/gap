package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.*;
import com.company.paw.models.*;
import com.company.paw.models.audits.Product;
import com.company.paw.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
    private final WeaponTypeRepository weaponTypeRepository;
    private final WeaponNameRepository weaponNameRepository;
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

    Weapon setWeapon(Weapon weapon, ProductInput input) {
        if (input.getSerial() != null)
            weapon.setSerial(input.getSerial());
        if (input.getOrganizationId() != null)
            weapon.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        if (input.getWeaponTypeId() != null)
            weapon.setType(weaponTypeRepository.findById(input.getWeaponTypeId()).orElse(null));
        if (input.getWeaponNameId() != null)
            weapon.setName(weaponNameRepository.findById(input.getWeaponNameId()).orElse(null));
        if (input.getWeaponCardImageId() != null)
            weapon.setWeaponCardImage(imageRepository.findById(input.getWeaponCardImageId()).orElse(null));
        if (input.getWeaponCardNumber() != null)
            weapon.setWeaponCardNumber(input.getWeaponCardNumber());
        weapon.setWeaponCardExpirationDate(stringToDate(input.getWeaponCardExpirationDate()));
        weapon.setStatus(input.isStatus());
        return weapon;
    }

    Plate plateInUse(Plate plate, Employee employee, Organization organization, ReportInput input) {
        handelReport(plate,employee,organization,input);
        plate.setPlateStatus(2);
        plate.setCurrentUser(employee);
        plate.setOrganization(organization);

        LinkedList<Plate> plates = employee.getPlates();
        plates.add(plate);
        employee.setPlates(plates);

        plates = organization.getPlates();
        plates.add(plate);
        organization.setPlates(plates);
        return plateRepository.save(plate);
    }

    Weapon weaponInUse(Weapon weapon, Employee employee, Organization organization, ReportInput input) {
        handelReport(weapon,employee,organization,input);
        weapon.setCurrentUser(employee);
        weapon.setOrganization(organization);

        LinkedList<Weapon> weapons = employee.getWeapons();
        weapons.add(weapon);
        employee.setWeapons(weapons);

        weapons = organization.getWeapons();
        weapons.add(weapon);
        organization.setWeapons(weapons);

        return weaponRepository.save(weapon);
    }

    private void handelReport(Product product,Employee employee, Organization organization, ReportInput input){
        Report report = setReport(new Report(), input);
        LinkedList<Report> reports = product.getReports();
        reports.add(report);
        product.setReports(reports);

        reports = employee.getReports();
        reports.add(report);
        employee.setReports(reports);

        reports = organization.getReports();
        reports.add(report);
        organization.setReports(reports);

        organizationRepository.save(organization);
        employeeRepository.save(employee);
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
        LinkedList<Weapon> weapons;
        LinkedList<Plate> plates;
        LinkedList<Equipment> equipments;

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

        if (input.getEmployeeId() != null)
            report.setEmployee(employeeRepository.findById(input.getEmployeeId()).orElse(null));
        if (input.getOrganizationId() != null)
            report.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        if (input.getBorrowTime() != null)
            report.setBorrowTime(stringToDate(input.getBorrowTime()));
        if (input.getRequestId() != null)
            report.setRequest(requestRepository.findById(input.getRequestId()).orElse(null));
        if (input.getBorrowDescription() != null)
            report.setBorrowDescription(input.getBorrowDescription());
        if (input.getInformationLetterId() != null)
            report.setInformationLetter(imageRepository.findById(input.getInformationLetterId()).orElse(null));
        if (input.getArmyLetterId() != null)
            report.setArmyLetter(imageRepository.findById(input.getArmyLetterId()).orElse(null));
        if (input.getAcceptImageId() != null)
            report.setAcceptImage(imageRepository.findById(input.getAcceptImageId()).orElse(null));
        if (input.getReciteImageId() != null)
            report.setReciteImage(imageRepository.findById(input.getReciteImageId()).orElse(null));

        report.setBorrowStatus(input.isBorrowStatus());
        return reportRepository.save(report);
    }

    Request setRequest(Request request, RequestInput input) {
        request.setDescription(input.getDescription());
        request.setTitle(input.getTitle());
        request.setDate(stringToDate(input.getDateOnImage()));
        request.setEmployee(employeeRepository.findById(input.getEmployeeId()).orElse(null));
        request.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        request.setImage(imageRepository.findById(input.getImageId()).orElse(null));
        return request;
    }

    private Date stringToDate(String stringDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(stringDate);
        } catch (Exception ignored) {
        }
        return date;
    }
}
