package com.company.paw.models.audits;

import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@GraphQLInterface(name = "Product")
public abstract class Product extends AuditModel {
    private String serial;
    private Organization organization;
    private Employee currentUser;
    private boolean status;
}
