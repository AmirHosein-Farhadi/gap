package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.models.*;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        weapon.setRecords(recordsService.recordsIdToRecords(input.getRecordsId()));
        weapon.setImages(imageService.imagesIdToImages(input.getImagesId()));
        weapon.setType(weaponTypeOptional.orElse(null));
        return weapon;
    }
}
