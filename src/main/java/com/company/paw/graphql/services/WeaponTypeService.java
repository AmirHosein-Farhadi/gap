package com.company.paw.graphql.services;

import com.company.paw.models.WeaponName;
import com.company.paw.models.WeaponType;
import com.company.paw.repositories.WeaponNameRepository;
import com.company.paw.repositories.WeaponTypeRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeaponTypeService {
    private final WeaponTypeRepository weaponTypeRepository;
    private final WeaponNameRepository weaponNameRepository;

    @GraphQLQuery
    public List<WeaponType> allWeaponTypes() {
        return weaponTypeRepository.findAll();
    }

    @GraphQLQuery
    public WeaponType getWeaponTypes(String id) {
        return weaponTypeRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public WeaponType addWeaponTypes(String name) {
        return weaponTypeRepository.save(new WeaponType(name, 0, new LinkedList<>(), new LinkedList<>()));
    }

    @GraphQLMutation
    public WeaponType editWeaponType(String id, String name) {
        WeaponType weaponType = weaponTypeRepository.findById(id).get();
        weaponType.setName(name);
        return weaponTypeRepository.save(weaponType);
    }

    @GraphQLMutation
    public WeaponType deleteWeaponType(String weaponTypeId) {
        Optional<WeaponType> weaponTypeOptional = weaponTypeRepository.findById(weaponTypeId);
        weaponTypeOptional.ifPresent(weaponTypeRepository::delete);
        return weaponTypeOptional.orElse(null);
    }

    @GraphQLMutation
    public WeaponType addWeaponNameToType(String weaponTypeId, String weaponName) {
        WeaponName name = weaponNameRepository.save(new WeaponName(weaponName));
        WeaponType weaponType = weaponTypeRepository.findById(weaponTypeId).get();
        LinkedList<WeaponName> weaponNames = weaponType.getWeaponNames();
        weaponNames.add(name);
        weaponType.setWeaponNames(weaponNames);
        return weaponTypeRepository.save(weaponType);
    }
}
