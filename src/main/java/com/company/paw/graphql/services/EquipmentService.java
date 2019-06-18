package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.ProductInput;
import com.company.paw.models.Equipment;
import com.company.paw.repositories.EquipmentRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<Equipment> equipments = equipmentRepository.findAll();
        for (Equipment e : equipments)
            if (e.getType() != 1)
                equipments.remove(e);
        return equipments;
    }

    @GraphQLQuery
    public List<Equipment> allShocker() {
        List<Equipment> equipments = equipmentRepository.findAll();
        for (Equipment e : equipments)
            if (e.getType() != 2)
                equipments.remove(e);
        return equipments;
    }

    @GraphQLQuery
    public List<Equipment> allTalkie() {
        List<Equipment> equipments = equipmentRepository.findAll();
        for (Equipment e : equipments)
            if (e.getType() != 3)
                equipments.remove(e);
        return equipments;
    }

    @GraphQLQuery
    public Equipment getEquipment(String id) {
        return equipmentRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Equipment addEquipment(ProductInput input, int equipmentType) {

    }
}
