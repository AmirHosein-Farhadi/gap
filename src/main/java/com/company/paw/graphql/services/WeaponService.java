package com.company.paw.graphql.services;

import com.company.paw.Repositories.ImageRepository;
import com.company.paw.Repositories.OrganizationRepository;
import com.company.paw.Repositories.WeaponRepository;
import com.company.paw.Repositories.WeaponTypeRepository;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.models.Image;
import com.company.paw.models.Organization;
import com.company.paw.models.Weapon;
import com.company.paw.models.WeaponType;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeaponService {
    private final WeaponRepository weaponRepository;
    private final WeaponTypeRepository weaponTypeRepository;
    private final OrganizationRepository organizationRepository;
    private final ImageRepository imageRepository;
    private final ReportsService reportsService;
    private final EmployeeService employeeService;

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
        return weaponRepository.save(addInput(input));
    }

    private Weapon addInput(ProductInput input) {
        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());
        Optional<Image> imageOptional = imageRepository.findById(input.getImageId());
        Optional<WeaponType> weaponTypeOptional = weaponTypeRepository.findById(input.getWeaponTypeId());

        Weapon weapon = new Weapon();
        if (input.getCurrentUsersId() != null && !input.getCurrentUsersId().isEmpty())
            weapon.setCurrentUsers(employeeService.employeesIdToEmployees(input.getCurrentUsersId()));
        else
            weapon.setCurrentUsers(Collections.emptyList());

        if (weapon.getCurrentUsers().size() == 1)
            weapon.setSerial(input.getSerial());
        else
            weapon.setSerial(null);

        weapon.setOrganization(organizationOptional.orElse(null));

        if (input.getReportsId() != null && !input.getReportsId().isEmpty()) {
            weapon.setReports(reportsService.recordsIdToRecords(input.getReportsId()));
        } else {
            weapon.setReports(Collections.emptyList());
        }
        weapon.setImage(imageOptional.orElse(null));
        weapon.setName(input.getName());
        weapon.setType(weaponTypeOptional.orElse(null));

        return weapon;
    }
}
