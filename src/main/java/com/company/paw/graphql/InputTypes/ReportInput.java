package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportInput {
    private String userId;
    private String organizationId;
    private String productId;
    private String requestId;
    private String borrowTime;
    private boolean borrowStatus;
    private String description;
}
