package com.company.paw.graphql.services;

import com.company.paw.Repositories.WeaponTypeRepository;
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
public class WeaponTypeService {
    private final WeaponTypeRepository weaponTypeRepository;

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
        return weaponTypeRepository.save(new WeaponType(name, 0, Collections.emptyList()));
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
}
