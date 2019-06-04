package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationInput {
    private String name;
    private String city;
    private String address;
    private String phoneNumber;
    private String username;
    private String password;
    private String avatar;
}
