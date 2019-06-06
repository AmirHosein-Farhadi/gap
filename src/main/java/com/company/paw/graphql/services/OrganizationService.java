package com.company.paw.graphql.services;

import com.company.paw.Repositories.OrganizationRepository;
import com.company.paw.graphql.InputTypes.OrganizationInput;
import com.company.paw.models.Organization;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    @GraphQLQuery
    public List<Organization> allOrganization() {
        return organizationRepository.findAll();
    }

    @GraphQLQuery
    public Organization getEmployee(String id) {
        return organizationRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Organization addOrganization(OrganizationInput input) {
        return organizationRepository.save(addInput(input));
    }

    @GraphQLMutation
    public Organization updateOrganization(OrganizationInput input, String id) {

    }

    @GraphQLMutation
    private Organization updateInput(OrganizationInput input, String id) {
        Organization organization = organizationRepository.findById(id).get();
        if (!input.getName().isEmpty())
            organization.setName();
        if (!input.getName().isEmpty())
            organization.setName();
        if (!input.getName().isEmpty())
            organization.setName();
        if (!input.getName().isEmpty())
            organization.setName();

    }

    private Organization addInput(OrganizationInput input) {
        return Organization.builder()
                .name(input.getName())
                .city(input.getCity())
                .address(input.getAddress())
                .phoneNumber(input.getPhoneNumber())
                .username(input.getUsername())
                .password(input.getPassword())
                .build();
    }
}
