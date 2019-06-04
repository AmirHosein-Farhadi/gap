package com.company.paw.graphql.services;

import com.company.paw.Repositories.OrganizationRepository;
import com.company.paw.graphql.InputTypes.OrganizationInput;
import com.company.paw.models.Organization;
import io.leangen.graphql.annotations.GraphQLMutation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    @GraphQLMutation
    public Organization addOrganization(OrganizationInput input) {
        return organizationRepository.save(addInput(input));
    }

    private Organization addInput(OrganizationInput input) {
        return Organization.builder()
                .name(input.getName())
                .city(input.getCity())
                .address(input.getAddress())
                .phoneNumber(input.getPhoneNumber())
                .username(input.getUsername())
                .password(input.getPassword())
                .avatar(input.getAvatar())
                .build();
    }
}
