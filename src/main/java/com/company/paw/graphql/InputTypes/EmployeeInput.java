package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {
    private String name;
    private String nationalId;
    private String employeeId;
    private String address;
    private String phoneNumber;
    private String positionId;
    private String organizationId;
    private List<String> images;
    private boolean isManager;
}
