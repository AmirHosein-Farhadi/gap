package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import com.company.paw.models.Audits.Product;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"products", "employees"}, callSuper = false)
@AllArgsConstructor
@Builder
@Document
@GraphQLType
public class Organization extends AuditModel {
    private String name;
    //todo add entity
    private String state;
    private String city;

    private String address;
    private String phoneNumber;
    //to be decided after test
    private String username;
    private String password;

    private List<Employee> employees;
    private List<Product> products;
}
