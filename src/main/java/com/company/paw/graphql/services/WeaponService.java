package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.graphql.InputTypes.ReportInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Report;
import com.company.paw.models.goods.Weapon;
import com.company.paw.models.goods.WeaponType;
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
public class WeaponService {
    private final WeaponRepository weaponRepository;
    private final WeaponTypeRepository weaponTypeRepository;
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final RequestRepository requestRepository;
    private final ReportRepository reportRepository;
    private final ImageRepository imageRepository;

    @GraphQLQuery
    public List<Weapon> allWeapons() {
        List<Weapon> weapons = weaponRepository.findAll();
        for (Weapon weapon : weapons)
            if (weapon.getType().getName().equals("افشانه") || weapon.getType().getName().equals("شوکر") || weapon.getType().getName().equals("بیسیم"))
                weapons.remove(weapon);
        return weapons;
    }

    @GraphQLQuery
    public List<Weapon> allShockers() {
        List<Weapon> weapons = weaponRepository.findAll();
        for (Weapon weapon : weapons)
            if (!weapon.getType().getName().equals("شوکر"))
                weapons.remove(weapon);
        return weapons;
    }

    @GraphQLQuery
    public List<Weapon> allSprays() {
        List<Weapon> weapons = weaponRepository.findAll();
        for (Weapon weapon : weapons)
            if (!weapon.getType().getName().equals("افشانه"))
                weapons.remove(weapon);
        return weapons;
    }

    @GraphQLQuery
    public List<Weapon> allBisims() {
        List<Weapon> weapons = weaponRepository.findAll();
        for (Weapon weapon : weapons)
            if (!weapon.getType().getName().equals("بیسیم"))
                weapons.remove(weapon);
        return weapons;
    }

    @GraphQLQuery
    public Weapon getWeapon(String id) {
        return weaponRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Weapon addWeapon(ProductInput input) {
        Weapon weapon = addInput(input);
        weaponRepository.save(weapon);

        WeaponType weaponType = weaponTypeRepository.findById(weapon.getType().getId()).get();
        weaponType.setQuantity(weaponType.getQuantity() + 1);
        List<Weapon> weapons = weaponType.getWeapons();
        weapons.add(weapon);
        weaponType.setWeapons(weapons);
        weaponTypeRepository.save(weaponType);

        return weapon;
    }

    @GraphQLMutation
    public Weapon editWeapon(String weaponId, ProductInput input) {
        return weaponRepository.save(editInput(weaponId, input));
    }

    @GraphQLMutation
    public Weapon recieveWeapon(ReportInput input) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getBorrowTime());
        } catch (Exception ignored) {
        }
        Weapon weapon = weaponRepository.findById(input.getProductId()).orElse(null);
        Employee employee = employeeRepository.findById(input.getEmployeeId()).orElse(null);

        Report report = new Report();
        report.setEmployee(employee);
        report.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        report.setProduct(weaponRepository.findById(input.getProductId()).orElse(null));
        report.setRequest(requestRepository.findById(input.getRequestId()).orElse(null));
        report.setBorrowTime(date);
        report.setBorrowStatus(input.isBorrowStatus());
        report.setBorrowDescription(input.getBorrowDescription());
        report.setAcceptImage(imageRepository.findById(input.getAcceptImageId()).orElse(null));
        report.setReciteImage(imageRepository.findById(input.getReciteImageId()).orElse(null));
        report.setInformationLetter(imageRepository.findById(input.getInformationLetterId()).orElse(null));
        report.setReciteImage(imageRepository.findById(input.getReciteImageId()).orElse(null));
        reportRepository.save(report);

        weapon.setCurrentUser(employee);
        List<Report> reports = weapon.getReports();
        List<Weapon> weapons = employee.getWeapons();
        weapons.add(weapon);
        employee.setWeapons(weapons);
        reports.add(report);
        weapon.setReports(reports);
        employeeRepository.save(employee);
        return weaponRepository.save(weapon);
    }


    @GraphQLMutation
    public Weapon returnWeapon(String weaponId, boolean returnStatus, String returnDate, String returnDescription) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(returnDate);
        } catch (Exception ignored) {
        }
        Weapon weapon = weaponRepository.findById(weaponId).get();
        weapon.setCurrentUser(null);
        weaponRepository.save(weapon);

        List<Report> reports = weapon.getReports();
        Report report = reports.get(reports.size() - 1);
        report.setReturnStatus(returnStatus);
        report.setReturnDescription(returnDescription);
        report.setReturnTime(date);
        reportRepository.save(report);

        Employee employee = employeeRepository.findById(weapon.getCurrentUser().getId()).orElse(null);
        List<Weapon> weapons = employee.getWeapons();
        weapons.remove(weapon);
        employee.setWeapons(weapons);
        employeeRepository.save(employee);
        return weapon;
    }

    @GraphQLMutation
    public Weapon deleteWeapon(String weaponId) {
        Optional<Weapon> weaponOptional = weaponRepository.findById(weaponId);

        WeaponType weaponType = weaponTypeRepository.findById(weaponOptional.get().getType().getId()).get();
        weaponType.setQuantity(weaponType.getQuantity() + -1);
        List<Weapon> weapons = weaponType.getWeapons();
        weapons.remove(weaponOptional.get());
        weaponType.setWeapons(weapons);
        weaponTypeRepository.save(weaponType);

        weaponOptional.ifPresent(weaponRepository::delete);
        return weaponOptional.orElse(null);
    }

    private Weapon editInput(String weaponId, ProductInput input) {
        Date weaponExpirationDate = null;
        try {
            weaponExpirationDate = new SimpleDateFormat("yyyy/MM/dd").parse(input.getWeaponCardExpirationDate());
        } catch (Exception ignored) {
        }
        Weapon weapon = weaponRepository.findById(weaponId).get();
        if (input.getSerial() != null)
            weapon.setSerial(input.getSerial());
        if (input.getOrganizationId() != null)
            weapon.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        if (input.getWeaponName() != null)
            weapon.setName(input.getWeaponName());
        if (input.getWeaponTypeId() != null)
            weapon.setType(weaponTypeRepository.findById(input.getWeaponTypeId()).get());
        if (weaponExpirationDate != null)
            weapon.setWeaponCardExpirationDate(weaponExpirationDate);
        if (input.getWeaponCardNumber() != null)
            weapon.setWeaponCardNumber(input.getWeaponCardNumber());
        if (input.getWeaponCardImageId() != null)
            weapon.setWeaponCardImage(imageRepository.findById(input.getWeaponCardImageId()).orElse(null));
        return weapon;
    }

    private Weapon addInput(ProductInput input) {
        Date weaponExpirationDate = null;
        try {
            weaponExpirationDate = new SimpleDateFormat("yyyy/MM/dd").parse(input.getWeaponCardExpirationDate());
        } catch (Exception ignored) {
        }
        Weapon weapon = new Weapon();

        weapon.setSerial(input.getSerial());
        weapon.setWeaponCardExpirationDate(weaponExpirationDate);
        weapon.setWeaponCardNumber(input.getWeaponCardNumber());
        weapon.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        weapon.setName(input.getWeaponName());
        if (input.getWeaponTypeId().length() < 7)
            weapon.setType(weaponTypeRepository.findByName(input.getWeaponTypeId()).get());
        else
            weapon.setType(weaponTypeRepository.findById(input.getWeaponTypeId()).get());

        if (input.getWeaponCardImageId() != null)
            weapon.setWeaponCardImage(imageRepository.findById(input.getWeaponCardImageId()).orElse(null));
        weapon.setReports(Collections.emptyList());
        weapon.setCurrentUser(null);
        return weapon;
    }
}
