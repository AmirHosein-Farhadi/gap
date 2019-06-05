package com.company.paw.graphql.InputTypes;

import com.company.paw.models.Audits.Product;
import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordInput {
    private Employee user;
    private Product product;
    private Organization organization;
    private Date time;
    private boolean status;
    private boolean isReturning;
    private String description;
}
