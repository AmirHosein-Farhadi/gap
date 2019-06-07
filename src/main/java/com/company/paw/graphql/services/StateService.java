package com.company.paw.graphql.services;

import com.company.paw.Repositories.StateRepository;
import com.company.paw.models.City;
import com.company.paw.models.State;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class StateService {
    private final StateRepository stateRepository;

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
    public State addSubCity(State state, City city) {
        State s = stateRepository.findById(state.getId()).orElse(null);
        if (s != null)
            s.addSubCity(city);
        return s;
    }
}
