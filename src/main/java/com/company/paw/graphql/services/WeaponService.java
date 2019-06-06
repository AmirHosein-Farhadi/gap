package com.company.paw.graphql.services;

import com.company.paw.Repositories.WeaponRepository;
import com.company.paw.models.Weapon;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WeaponService {
    private final WeaponRepository weaponRepository;

    @GraphQLQuery
    public List<Weapon> allWeapons() {
        return weaponRepository.findAll();
    }

    @GraphQLQuery
    public Weapon getWeapon(String id) {
        return weaponRepository.findById(id).orElse(null);
    }
}
