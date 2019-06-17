package com.company.paw.graphql.services;

import com.company.paw.models.WeaponName;
import com.company.paw.repositories.WeaponNameRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeaponNameService {
    private final WeaponNameRepository weaponNameRepository;

    @GraphQLQuery
    public List<WeaponName> allWeaponsName() {
        return weaponNameRepository.findAll();
    }

    @GraphQLQuery
    public WeaponName getWeaponName(String id) {
        return weaponNameRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public WeaponName addWeaponName(String name) {
        return weaponNameRepository.save(new WeaponName(name));
    }

    @GraphQLMutation
    public WeaponName editWeaponName(String id, String name) {
        WeaponName weaponName = weaponNameRepository.findById(id).get();
        weaponName.setName(name);
        return weaponNameRepository.save(weaponName);
    }

    @GraphQLMutation
    public WeaponName deleteWeaponName(String id) {
        Optional<WeaponName> weaponNameOptional = weaponNameRepository.findById(id);
        weaponNameOptional.ifPresent(weaponNameRepository::delete);
        return weaponNameOptional.orElse(null);
    }
}
