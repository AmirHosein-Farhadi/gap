package com.company.paw.graphql.services;

import com.company.paw.models.City;
import com.company.paw.models.State;
import com.company.paw.repositories.CityRepository;
import com.company.paw.repositories.StateRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @GraphQLQuery
    public List<City> allCities() {
        return cityRepository.findAll();
    }

    @GraphQLQuery
    public City getCity(String id) {
        return cityRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public City addCity(String name, String stateId) {
        Optional<State> stateOptional = stateRepository.findById(stateId);
        return cityRepository.save(new City(name, stateOptional.orElse(null)));
    }
}
