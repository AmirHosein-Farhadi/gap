package com.company.paw.graphql.services;

import com.company.paw.Repositories.ImageRepository;
import com.company.paw.Repositories.OrganizationRepository;
import com.company.paw.Repositories.WeaponRepository;
import com.company.paw.Repositories.WeaponTypeRepository;
import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.models.Weapon;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WeaponService {
    private final WeaponRepository weaponRepository;
    private final WeaponTypeRepository weaponTypeRepository;
    private final OrganizationRepository organizationRepository;
    private final ImageRepository imageRepository;

    @GraphQLQuery
    public List<Weapon> allWeapons() {
        return weaponRepository.findAll();
    }

    @GraphQLQuery
    public Weapon getWeapon(String id) {
        return weaponRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Weapon addWeapon(ProductInput input) {
        return weaponRepository.save(addInput(input));
    }

    @GraphQLMutation
    public Weapon editWeapon(String weaponId, ProductInput input) {
        return weaponRepository.save(editInput(weaponId, input));
    }

    @GraphQLMutation
    public Weapon deleteWeapon(String weaponId) {
        Optional<Weapon> weaponOptional = weaponRepository.findById(weaponId);
        weaponOptional.ifPresent(weaponRepository::delete);
        return weaponOptional.orElse(null);
    }

    private Weapon editInput(String weaponId, ProductInput input) {
        Weapon weapon = weaponRepository.findById(weaponId).get();
        if (input.getSerial() != null)
            weapon.setSerial(input.getSerial());
        if (input.getOrganizationId() != null)
            weapon.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        if (input.getImageId() != null)
            weapon.setImage(imageRepository.findById(input.getImageId()).orElse(null));
        if (input.getWeaponName() != null)
            weapon.setName(input.getWeaponName());
        if (input.getWeaponTypeId() != null)
            weapon.setType(weaponTypeRepository.findById(input.getWeaponTypeId()).get());
        return weapon;
    }

    private Weapon addInput(ProductInput input) {
        Weapon weapon = new Weapon();

        weapon.setSerial(input.getSerial());
        weapon.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        weapon.setImage(imageRepository.findById(input.getImageId()).orElse(null));
        weapon.setName(input.getWeaponName());
        weapon.setType(weaponTypeRepository.findById(input.getWeaponTypeId()).get());
        weapon.setReports(Collections.emptyList());
        weapon.setCurrentUsers(Collections.emptyList());
        return weapon;
    }
}
