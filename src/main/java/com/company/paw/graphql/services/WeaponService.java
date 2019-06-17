package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.graphql.InputTypes.ReportInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import com.company.paw.models.Weapon;
import com.company.paw.models.WeaponType;
import com.company.paw.repositories.*;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
    private final ConvertService convertService;

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
        Weapon weapon = convertService.setWeapon(new Weapon(), input);
        weapon.setReports(new LinkedList<>());
        weapon.setCurrentUser(null);
        weaponRepository.save(weapon);

        WeaponType weaponType = weaponTypeRepository.findById(weapon.getType().getId()).get();
        weaponType.setQuantity(weaponType.getQuantity() + 1);
        LinkedList<Weapon> weapons = weaponType.getWeapons();
        weapons.add(weapon);
        weaponType.setWeapons(weapons);
        weaponTypeRepository.save(weaponType);

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
        Organization organization = organizationRepository.findById(input.getOrganizationId()).orElse(null);
        return convertService.weaponInUse(weapon, employee, organization, input);
    }

    @GraphQLMutation
    public Weapon returnWeapon(String weaponId, boolean returnStatus, String returnDate, String returnDescription) {
        return (Weapon) convertService.returnProduct(weaponId, returnStatus, returnDate, returnDescription);
    }

    @GraphQLMutation
    public Weapon deleteWeapon(String weaponId) {
        Optional<Weapon> weaponOptional = weaponRepository.findById(weaponId);

        WeaponType weaponType = weaponTypeRepository.findById(weaponOptional.get().getType().getId()).get();
        weaponType.setQuantity(weaponType.getQuantity() + -1);
        LinkedList<Weapon> weapons = weaponType.getWeapons();
        weapons.remove(weaponOptional.get());
        weaponType.setWeapons(weapons);
        weaponTypeRepository.save(weaponType);

        weaponOptional.ifPresent(weaponRepository::delete);
        return weaponOptional.orElse(null);
    }
}
