package com.company.paw.graphql.services;

import com.company.paw.Repositories.WeaponCategoryRepository;
import com.company.paw.models.WeaponCategory;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WeaponCategoryService {
    private final WeaponCategoryRepository weaponCategoryRepository;

    @GraphQLQuery
    public List<WeaponCategory> allWeaponCategories() {
        return weaponCategoryRepository.findAll();
    }

    @GraphQLQuery
    public WeaponCategory getWeaponCategories(String id) {
        return weaponCategoryRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public WeaponCategory addWeaponCategory(String name) {
        return weaponCategoryRepository.save(new WeaponCategory(name));
    }

    @GraphQLMutation
    public WeaponCategory editWeaponCategory(String id, String name) {
        WeaponCategory weaponCategory = weaponCategoryRepository.findById(id).get();
        weaponCategory.setName(name);
        return weaponCategoryRepository.save(weaponCategory);

    }
}
