package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.models.Equipment;
import com.company.paw.repositories.EquipmentRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final ConvertService convertService;

    @GraphQLQuery
    public List<Equipment> allEquipments() {
        return equipmentRepository.findAll();
    }

    @GraphQLQuery
    public List<Equipment> allSprays() {
        return equipmentRepository.findByType(1);
    }

    @GraphQLQuery
    public List<Equipment> allShocker() {
        return equipmentRepository.findByType(2);
    }

    @GraphQLQuery
    public List<Equipment> allTalkie() {
        return equipmentRepository.findByType(3);
    }

    @GraphQLQuery
    public Equipment getEquipment(String id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Equipment addEquipment(ProductInput input, int equipmentType) {
        return equipmentRepository.save(convertService.setEquipment(new Equipment(), equipmentType, input));
    }

    @GraphQLMutation
    public Equipment editEquipment(String equipmentId, ProductInput input) {
        Equipment equipment;
        if (equipmentRepository.findById(equipmentId).isPresent())
            equipment = equipmentRepository.findById(equipmentId).get();
        else
            return null;

        return equipmentRepository.save(convertService.setEquipment(equipment, equipment.getType(), input));
    }

    @GraphQLMutation
    public Equipment deleteEquipment(String equipmentId) {
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(equipmentId);
        equipmentOptional.ifPresent(equipmentRepository::delete);
        return equipmentOptional.orElse(null);
    }
}
