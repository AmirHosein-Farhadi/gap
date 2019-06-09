package com.company.paw.graphql.services;

import com.company.paw.Repositories.CityRepository;
import com.company.paw.Repositories.StateRepository;
import com.company.paw.models.City;
import com.company.paw.models.State;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    @GraphQLQuery
    public List<State> allStates() {
        return stateRepository.findAll();
    }

    @GraphQLQuery
    public State getState(String id) {
        return stateRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public State addState(String name) {
        return stateRepository.save(new State(name, Collections.emptyList()));
    }

    @GraphQLMutation
    public State addSubCity(String stateId, String cityId) {
        State state = stateRepository.findById(stateId).get();
        City city = cityRepository.findById(cityId).get();
        return stateRepository.save(addCityToState(state, city));
    }

    private State addCityToState(State state, City city) {
        state.getCities().add(city);
        return state;
    }

}
