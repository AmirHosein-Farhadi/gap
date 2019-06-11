package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportInput {
    private String employeeId;
    private String organizationId;
    private String productId;
    private String requestId;
    private String borrowTime;
    private boolean borrowStatus;
    private String borrowDescription;
    private String informationLetterId;
    private String armyLetterId;
    private String acceptImageId;
    private String reciteImageId;
}
