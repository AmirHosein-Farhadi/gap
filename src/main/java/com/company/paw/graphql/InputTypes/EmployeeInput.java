package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {
    private String fullName;
    private String birthDate;
    private String address;
    private String phoneNumber;
    private String nationalId;
    private String positionId;
    private String organizationId;
    private String imageId;
}
