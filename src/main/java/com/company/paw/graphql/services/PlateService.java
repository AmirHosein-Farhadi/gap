package com.company.paw.graphql.services;

import com.company.paw.Repositories.PlateRepository;
import com.company.paw.models.Plate;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlateService {
    private final PlateRepository plateRepository;

    @GraphQLQuery
    public List<Plate> allPlates() {
        return plateRepository.findAll();
    }

    @GraphQLQuery
    public Plate getPlate(String id) {
        return plateRepository.findById(id).orElse(null);
    }
}
