package com.company.paw.graphql.services;

import com.company.paw.repositories.CityRepository;
import com.company.paw.repositories.OrganizationRepository;
import com.company.paw.repositories.StateRepository;
import com.company.paw.graphql.InputTypes.OrganizationInput;
import com.company.paw.models.City;
import com.company.paw.models.Organization;
import com.company.paw.models.State;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@GraphQLApi
@AllArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @GraphQLQuery
    public List<Organization> allOrganization() {
        return organizationRepository.findAll();
    }

    @GraphQLQuery
    public Organization getOrganization(String organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    @GraphQLMutation
    public Organization addOrganization(OrganizationInput input) {
        return organizationRepository.save(addInput(input));
    }

    @GraphQLMutation
    public Organization deleteOrganization(String organizationId) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        organizationOptional.ifPresent(organizationRepository::delete);
        return organizationOptional.orElse(null);
    }

    @GraphQLMutation
    public Organization editOrganization(String organizationId, OrganizationInput input) {
        return organizationRepository.save(editInput(organizationId, input));
    }

    private Organization addInput(OrganizationInput input) {
        State state = new State();
        state.setName("تهران");
        City city = new City();
        city.setName("تهران");
        city.setState(state);
        state.setCities(Collections.singletonList(city));
        return Organization.builder()
                .name(input.getName())
                .address(input.getAddress())
                .state(state)
                .city(city)
                .reports(Collections.emptyList())
                .employees(Collections.emptyList())
                .weapons(Collections.emptyList())
                .plates(Collections.emptyList())
                .build();
    }

    private Organization editInput(String organizationId, OrganizationInput input) {
        Organization organization = organizationRepository.findById(organizationId).get();
        if (input.getName() != null)
            organization.setName(input.getName());
        if (input.getAddress() != null)
            organization.setAddress(input.getAddress());
        if (input.getCityId() != null)
            organization.setCity(cityRepository.findById(input.getCityId()).get());
        if (input.getStateId() != null)
            organization.setState(stateRepository.findById(input.getStateId()).get());
        return organization;
    }
}
