package com.company.paw.graphql.InputTypes;

import com.company.paw.models.City;
import com.company.paw.models.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationInput {
    private State state;
    private City city;
    private String name;
    private String address;
    private String phoneNumber;
    private String username;
    private String password;
}
