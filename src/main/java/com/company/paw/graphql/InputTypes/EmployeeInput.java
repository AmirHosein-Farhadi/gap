package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {
    private String fullName;
    private String birthDate;
    private String address;
    private String phoneNumber;
    private String nationalId;
    private String employeeCardNumber;
    private String employeeCardExpirationDate;

    private String employeeCardImageId;
    private String positionId;
    private String organizationId;
    private String imageId;
    private String sprayId;
    private String shockerId;
    private String talkieId;
}
