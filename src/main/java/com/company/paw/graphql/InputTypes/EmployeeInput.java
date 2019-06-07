package com.company.paw.graphql.InputTypes;

import io.leangen.graphql.annotations.GraphQLNonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {
    @GraphQLNonNull
    private String fullName;

    @GraphQLNonNull
    private String nationalId;

    @GraphQLNonNull
    private String employeeId;

    @GraphQLNonNull
    private String positionId;

    @GraphQLNonNull
    private String organizationId;

    private String address;
    private String phoneNumber;
    private List<String> images;
}
