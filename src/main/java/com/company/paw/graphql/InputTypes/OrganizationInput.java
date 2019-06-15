package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationInput {
    private String name;
    private String address;
    private String stateId;
    private String cityId;
    private int bulletsQuantity;
//    private String username;
//    private String password;
}
