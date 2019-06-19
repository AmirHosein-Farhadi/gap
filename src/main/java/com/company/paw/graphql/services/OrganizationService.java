package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.OrganizationInput;
import com.company.paw.models.Organization;
import com.company.paw.repositories.OrganizationRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@GraphQLApi
@AllArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ConvertService convertService;

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
        Organization organization = convertService.setOrganization(new Organization(), input);
        organization.setReports(new LinkedList<>());
        organization.setEmployees(new LinkedList<>());
        organization.setWeapons(new LinkedList<>());
        organization.setPlates(new LinkedList<>());
        organization.setEquipments(new LinkedList<>());
        return organizationRepository.save(organization);
    }

    @GraphQLMutation
    public Organization deleteOrganization(String organizationId) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        organizationOptional.ifPresent(organizationRepository::delete);
        return organizationOptional.orElse(null);
    }

    @GraphQLMutation
    public Organization editOrganization(String organizationId, OrganizationInput input) {
        return organizationRepository.save(
                convertService.setOrganization(organizationRepository.findById(organizationId).get(), input)
        );
    }

    @GraphQLMutation
    public Organization editBullets(String organizationId, int numberOfBullets) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isPresent()) {
            organizationOptional.get().setBulletsQuantity(organizationOptional.get().getBulletsQuantity() + numberOfBullets);
            return organizationRepository.save(organizationOptional.get());
        } else
            return null;
    }
}
