package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.graphql.InputTypes.ReportInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Weapon;
import com.company.paw.repositories.EmployeeRepository;
import com.company.paw.repositories.OrganizationRepository;
import com.company.paw.repositories.WeaponRepository;
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
    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final ConvertService convertService;

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
        Weapon weapon = convertService.setWeapon(new Weapon(), input);
        weaponRepository.save(weapon);
        return weapon;
    }

    @GraphQLMutation
    public Weapon editWeapon(String weaponId, ProductInput input) {
        return weaponRepository.save(convertService.setWeapon(weaponRepository.findById(weaponId).get(), input));
    }

    @GraphQLMutation
    public Weapon recieveWeapon(ReportInput input) {
        Weapon weapon = weaponRepository.findById(input.getProductId()).orElse(null);
        Employee employee = employeeRepository.findById(input.getEmployeeId()).orElse(null);
        return convertService.weaponInUse(weapon, employee, input);
    }

    @GraphQLMutation
    public Weapon returnWeapon(String weaponId, boolean returnStatus, String returnDate, String returnDescription) {
        return (Weapon) convertService.returnProduct(weaponId, returnStatus, returnDate, returnDescription);
    }

    @GraphQLMutation
    public Weapon deleteWeapon(String weaponId) {
        Optional<Weapon> weaponOptional = weaponRepository.findById(weaponId);
        weaponOptional.ifPresent(weaponRepository::delete);
        return weaponOptional.orElse(null);
    }
}
