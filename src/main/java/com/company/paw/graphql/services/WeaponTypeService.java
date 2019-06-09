package com.company.paw.graphql.services;

import com.company.paw.Repositories.WeaponCategoryRepository;
import com.company.paw.Repositories.WeaponTypeRepository;
import com.company.paw.graphql.InputTypes.WeaponTypeInput;
import com.company.paw.models.WeaponCategory;
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
    private final WeaponCategoryRepository weaponCategoryRepository;

    @GraphQLQuery
    public List<WeaponType> allWeaponTypes() {
        return weaponTypeRepository.findAll();
    }

    @GraphQLQuery
    public WeaponType getWeaponTypes(String id) {
        return weaponTypeRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public WeaponType addWeaponTypes(WeaponTypeInput input) {
        Optional<WeaponCategory> weaponCategoryOptional = weaponCategoryRepository.findById(input.getWeaponCategoryId());
        return weaponTypeRepository.save(new WeaponType(input.getName(), weaponCategoryOptional.orElse(null), Collections.emptyList()));
    }

    @GraphQLMutation
    public WeaponType editWeaponType(String id, WeaponTypeInput input) {
        WeaponType weaponType = weaponTypeRepository.findById(id).get();
        if (input.getWeaponCategoryId() != null)
            weaponType.setCategory(weaponCategoryRepository.findById(input.getWeaponCategoryId()).orElse(null));
        if (input.getName() != null)
            weaponType.setName(input.getName());
        return weaponTypeRepository.save(weaponType);
    }
}
