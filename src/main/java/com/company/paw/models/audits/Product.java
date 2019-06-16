package com.company.paw.models.audits;

import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import com.company.paw.models.Report;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"reports"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@GraphQLInterface(name = "Product")
public abstract class Product extends AuditModel {
    private String serial;
    private Organization organization;
    private Employee currentUser;
    private boolean status;

    @DBRef
    private LinkedList<Report> reports;
}
