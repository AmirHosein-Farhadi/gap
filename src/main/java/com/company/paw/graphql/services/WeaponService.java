package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.graphql.InputTypes.ProductReturnInput;
import com.company.paw.models.*;
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
    private final RequestRepository requestRepository;
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final RecordRepository recordRepository;
    private final ImageService imageService;
    private final RecordsService recordsService;
    private final WeaponTypeRepository weaponTypeRepository;

    @GraphQLQuery
    public List<Weapon> allWeapons() {
        return weaponRepository.findAll();
    }

    @GraphQLQuery
    public Weapon getWeapon(String id) {
        return weaponRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Weapon useWeapon(ProductReturnInput input) {
        Weapon weapon = weaponRepository.findById(input.getProductId()).get();
        Employee employee = employeeRepository.findById(input.getEmployeeId()).get();

        Record record = new Record();
        record.setUser(employee);
        record.setOrganization(weapon.getOrganization());
        record.setProduct(weapon);
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

        weapon.getRecords().add(record);
        weapon.setCurrentUser(employee);
        //todo add status to Product
        return weaponRepository.save(weapon);
    }

    @GraphQLMutation
    public Weapon returnWeapon(ProductReturnInput input) {
        Weapon weapon = weaponRepository.findById(input.getProductId()).get();
        Employee employee = employeeRepository.findById(input.getEmployeeId()).get();

        Record record = new Record();
        record.setUser(employee);
        record.setOrganization(weapon.getOrganization());
        record.setProduct(weapon);
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

        weapon.getRecords().add(record);
        weapon.setCurrentUser(null);
        return weaponRepository.save(weapon);
    }

    @GraphQLMutation
    public Weapon addWeapon(ProductInput input) {
        Weapon weapon = addInput(input);
        weaponRepository.save(weapon);
        WeaponType weaponType = weapon.getType();
        weaponType.getWeapons().add(weapon);
        weaponTypeRepository.save(weaponType);
        return weapon;
    }

    private Weapon addInput(ProductInput input) {
        Optional<Request> requestOptional = requestRepository.findById(input.getRequestId());
        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());
        Optional<Employee> employeeOptional = employeeRepository.findById(input.getCurrentUserId());
        Optional<WeaponType> weaponTypeOptional = weaponTypeRepository.findById(input.getWeaponTypeId());

        Weapon weapon = new Weapon();
        weapon.setSerial(input.getSerial());
        weapon.setRequest(requestOptional.orElse(null));
        weapon.setOrganization(organizationOptional.orElse(null));
        weapon.setCurrentUser(employeeOptional.orElse(null));
        if (input.getRecordsId() != null && !input.getRecordsId().isEmpty())
            weapon.setRecords(recordsService.recordsIdToRecords(input.getRecordsId()));
        else
            weapon.setRecords(Collections.emptyList());
        if (input.getImagesId() != null && !input.getImagesId().isEmpty())
            weapon.setImages(imageService.imagesIdToImages(input.getImagesId()));
        else
            weapon.setImages(Collections.emptyList());
        weapon.setType(weaponTypeOptional.orElse(null));
        return weapon;
    }
}
