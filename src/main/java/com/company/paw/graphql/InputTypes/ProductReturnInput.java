package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReturnInput {
    private String productId;
    private String employeeId;
    private boolean status;
    private String date;
    private String description;
}
