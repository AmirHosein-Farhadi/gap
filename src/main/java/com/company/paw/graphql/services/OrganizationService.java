package com.company.paw.graphql.services;

import com.company.paw.Repositories.CityRepository;
import com.company.paw.Repositories.OrganizationRepository;
import com.company.paw.Repositories.StateRepository;
import com.company.paw.graphql.InputTypes.OrganizationInput;
import com.company.paw.models.City;
import com.company.paw.models.Organization;
import com.company.paw.models.State;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Organization getOrganization(@GraphQLArgument(name = "id") String id) {
        return organizationRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Organization addOrganization(OrganizationInput input) {
        return organizationRepository.save(addInput(input));
    }

    @GraphQLMutation
    public Organization deleteOrganization(String id) {
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        organizationOptional.ifPresent(organizationRepository::delete);
        return organizationOptional.orElse(null);
    }

    @GraphQLMutation
    public Organization editOrganization(String id, OrganizationInput input) {
        return organizationRepository.save(editInput(id, input));
    }

    private Organization addInput(OrganizationInput input) {
        City city = cityRepository.findById(input.getCityId()).orElse(null);
        State state = stateRepository.findById(input.getStateId()).orElse(null);
        return Organization.builder()
                .name(input.getName())
                .city(city)
                .state(state)
                .address(input.getAddress())
                .phoneNumber(input.getPhoneNumber())
                .username(input.getUsername())
                .password(input.getPassword())
                .build();
    }

    private Organization editInput(String id, OrganizationInput input) {
        Organization organization = organizationRepository.findById(id).get();
        if (input.getAddress() != null)
            organization.setAddress(input.getAddress());
        if (input.getCityId() != null)
            organization.setCity(cityRepository.findById(input.getCityId()).orElse(null));
        if (input.getName() != null)
            organization.setName(input.getName());
        if (input.getPhoneNumber() != null)
            organization.setPhoneNumber(input.getPhoneNumber());
        if (input.getStateId() != null)
            organization.setState(stateRepository.findById(input.getStateId()).orElse(null));
        return organization;
    }
}
