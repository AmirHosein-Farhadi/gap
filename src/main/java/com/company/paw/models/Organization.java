package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import com.company.paw.models.Audits.Product;
import io.leangen.graphql.annotations.GraphQLNonNull;
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
    @GraphQLNonNull
    private String name;
    //todo add entity
    @GraphQLNonNull
    private String state;
    @GraphQLNonNull
    private String city;

    @GraphQLNonNull
    private String address;

    @GraphQLNonNull
    private String phoneNumber;
    //to be decided after test
    private String username;
    private String password;

    private List<Employee> employees;
    private List<Product> products;
}
