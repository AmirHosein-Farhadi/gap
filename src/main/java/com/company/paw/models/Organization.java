package com.company.paw.models;

import com.company.paw.models.audits.AuditModel;
import com.company.paw.models.goods.Plate;
import com.company.paw.models.goods.Weapon;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"reports", "employees", "weapons", "plates"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Organization extends AuditModel {
    private String name;
    private State state;
    private City city;
    private String address;

    private int bulletsQuantity;

    @DBRef
    private List<Report> reports;

    @DBRef
    private List<Employee> employees;

    @DBRef
    private List<Weapon> weapons;

    @DBRef
    private List<Plate> plates;

    //todo to be decided after test
//    private String username;
//    private String password;
}
