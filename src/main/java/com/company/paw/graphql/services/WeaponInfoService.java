package com.company.paw.graphql.services;

import com.company.paw.Repositories.WeaponInfoRepository;
import com.company.paw.graphql.InputTypes.WeaponInfoInput;
import com.company.paw.models.WeaponInfo;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WeaponInfoService {
    private final WeaponInfoRepository weaponInfoRepository;

    @GraphQLQuery
    public List<WeaponInfo> allWeaponInfos() {
        return weaponInfoRepository.findAll();
    }

    @GraphQLQuery
    public WeaponInfo getWeaponInfo(String id) {
        return weaponInfoRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public WeaponInfo addWeaponInfo(WeaponInfoInput weaponInfoInput) {
        return weaponInfoRepository.save(new WeaponInfo(weaponInfoInput.getType(), weaponInfoInput.getCategory()));
    }
}
