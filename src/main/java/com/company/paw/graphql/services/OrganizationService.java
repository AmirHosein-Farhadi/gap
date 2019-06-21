package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.OrganizationInput;
import com.company.paw.models.*;
import com.company.paw.repositories.*;
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
    private final RequestRepository requestRepository;
    private final EmployeeRepository employeeRepository;
    private final PlateRepository plateRepository;
    private final WeaponRepository weaponRepository;
    private final EquipmentRepository equipmentRepository;
    private final ConvertService convertService;

    @GraphQLQuery
    public List<Organization> allOrganization() {
        return organizationRepository.findAll();
    }

    @GraphQLQuery
    public Organization getOrganization(String organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    @GraphQLQuery
    public List<Request> organizationRequests(String organizationId) {
        return requestRepository.findByOrganizationId(organizationId);
    }

    @GraphQLQuery
    public List<Employee> organizationEmployees(String organizationId) {
        return employeeRepository.findByOrganizationId(organizationId);
    }

    @GraphQLQuery
    public List<Plate> organizationPlates(String organizationId) {
        return plateRepository.findByOrganizationId(organizationId);
    }
    @GraphQLQuery
    public List<Weapon> organizationWeapons(String organizationId) {
        return weaponRepository.findByOrganizationId(organizationId);
    }
    @GraphQLQuery
    public List<Equipment> organizationEquipments(String organizationId) {
        return equipmentRepository.findByOrganizationId(organizationId);
    }

    @GraphQLMutation
    public Organization addOrganization(OrganizationInput input) {
        Organization organization = convertService.setOrganization(new Organization(), input);
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
    public Organization editOrganizationBullets(String organizationId, int numberOfBullets) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isPresent()) {
            organizationOptional.get().setBulletsQuantity(numberOfBullets);
            return organizationRepository.save(organizationOptional.get());
        } else
            return null;
    }
}
