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

    Plate plateInUse(Plate plate, Employee employee, ReportInput input) {
        reportRepository.save(setReport(new Report(), input));
        plate.setPlateStatus(2);
        plate.setCurrentUser(employee);
        return plateRepository.save(plate);
    }

    Weapon weaponInUse(Weapon weapon, Employee employee, ReportInput input) {
        reportRepository.save(setReport(new Report(), input));
        weapon.setCurrentUser(employee);
        return weaponRepository.save(weapon);
    }

    Equipment equipmentInUse(Equipment equipment, Employee employee, ReportInput input) {
        reportRepository.save(setReport(new Report(), input));
        equipment.setCurrentUser(employee);
        return equipmentRepository.save(equipment);
    }

    Product returnProduct(String productId, boolean returnStatus, String returnDate, String returnDescription) {
        Optional<Plate> plateOptional = plateRepository.findById(productId);
        Optional<Weapon> weaponOptional = weaponRepository.findById(productId);
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(productId);

        Product product = null;
        if (plateOptional.isPresent())
            product = plateOptional.get();
        else if (weaponOptional.isPresent())
            product = weaponOptional.get();
        else if (equipmentOptional.isPresent())
            product = equipmentOptional.get();
        assert product != null;

        Employee employee = product.getCurrentUser();
        product.setCurrentUser(null);

        String type = product.getClass().getName();
        if (type.contains("Weapon"))
            weaponRepository.save((Weapon) product);
        else if (type.contains("Plate"))
            plateRepository.save((Plate) product);
        else if (type.contains("Equipment"))
            equipmentRepository.save((Equipment) product);

        if (type.contains("Equipment")) {
            Equipment equipment = (Equipment) product;
            int equipmentType = equipment.getType();
            if (equipmentType == 1)
                employee.setSpray(null);
            else if (equipmentType == 2)
                employee.setShocker(null);
            else if (equipmentType == 3)
                employee.setTalkie(null);
        }
        employeeRepository.save(employee);

        Report report = reportRepository.findByProductIdOrderByIdDesc(productId).get(0);
        report.setReturnStatus(returnStatus);
        report.setReturnTime(stringToDate(returnDate));
        report.setReturnDescription(returnDescription);
        reportRepository.save(report);

        return product;
    }

    Request setRequest(Request request, RequestInput input) {
        if (input.getDescription() != null)
            request.setDescription(input.getDescription());
        if (input.getTitle() != null)
            request.setTitle(input.getTitle());
        if (input.getDateOnImage() != null)
            request.setDate(stringToDate(input.getDateOnImage()));
        if (input.getEmployeeId() != null)
            request.setEmployee(employeeRepository.findById(input.getEmployeeId()).orElse(null));
        if (input.getOrganizationId() != null)
            request.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        if (input.getImageId() != null)
            request.setImage(imageRepository.findById(input.getImageId()).orElse(null));
        return request;
    }

    Equipment setEquipment(Equipment equipment, int equipmentType, ProductInput input) {
        Organization organization = null;
        if (input.getOrganizationId() != null)
            organization = organizationRepository.findById(input.getOrganizationId()).orElse(null);
        assert organization != null;
        equipment.setOrganization(organization);

        if (input.getSerial() != null)
            equipment.setSerial(input.getSerial());
        if (input.getWeaponCardExpirationDate() != null)
            equipment.setWeaponCardExpirationDate(stringToDate(input.getWeaponCardExpirationDate()));
        if (input.getWeaponCardNumber() != null)
            equipment.setWeaponCardNumber(input.getWeaponCardNumber());
        if (input.getWeaponCardImageId() != null)
            equipment.setWeaponCardImage(imageRepository.findById(input.getWeaponCardImageId()).orElse(null));
        equipment.setType(equipmentType);
        equipment.setStatus(input.isStatus());
        return equipment;
    }

    private Report setReport(Report report, ReportInput input) {
        Optional<Plate> plateOptional = plateRepository.findById(input.getProductId());
        Optional<Weapon> weaponOptional = weaponRepository.findById(input.getProductId());
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(input.getProductId());

        if (plateOptional.isPresent()) {
            report.setPlate(plateOptional.get());
            report.setProduct(plateOptional.get());
        } else if (weaponOptional.isPresent())
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

    private Date stringToDate(String stringDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(stringDate);
        } catch (Exception ignored) {
        }
        return date;
    }
}
