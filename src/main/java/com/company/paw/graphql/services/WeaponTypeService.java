package com.company.paw.graphql.services;

import com.company.paw.Repositories.WeaponTypeRepository;
import com.company.paw.models.WeaponType;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WeaponTypeService {
    private final WeaponTypeRepository weaponTypeRepository;

    @GraphQLQuery
    public List<WeaponType> allWeaponInfos() {
        return weaponTypeRepository.findAll();
    }

    @GraphQLQuery
    public WeaponType getWeaponInfo(String id) {
        return weaponTypeRepository.findById(id).orElse(null);
    }

//    @GraphQLMutation
//    public WeaponType addWeaponInfo(WeaponInfoInput weaponInfoInput) {
//        return weaponInfoRepository.save(new WeaponType(weaponInfoInput.getType(), weaponInfoInput.getCategory()));
//    }
}
