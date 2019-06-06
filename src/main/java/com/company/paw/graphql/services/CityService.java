package com.company.paw.graphql.services;

import com.company.paw.Repositories.CityRepository;
import com.company.paw.models.City;
import com.company.paw.models.State;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    @GraphQLQuery
    public List<City> allCities() {
        return cityRepository.findAll();
    }

    @GraphQLQuery
    public City getCity(String id) {
        return cityRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public City addCity(String name, State state) {
        return cityRepository.save(new City(name, state));
    }
}
