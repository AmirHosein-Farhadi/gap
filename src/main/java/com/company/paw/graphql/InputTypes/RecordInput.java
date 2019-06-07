package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordInput {
    private String userId;
    private String productId;
    private String organizationId;
    private String time;
    private boolean status;
    private boolean isReturning;
    private String description;
}
