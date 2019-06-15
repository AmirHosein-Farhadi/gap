package com.company.paw.graphql.services;

import com.company.paw.models.WeaponName;
import com.company.paw.repositories.WeaponNameRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WeaponNameService {
    private final WeaponNameRepository weaponNameRepository;

    @GraphQLQuery
    public List<WeaponName> allWeaponsName() {
        return weaponNameRepository.findAll();
    }
}
