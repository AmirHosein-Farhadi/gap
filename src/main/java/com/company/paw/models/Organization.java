package com.company.paw.models;

import com.company.paw.models.audits.AuditModel;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Data
@EqualsAndHashCode(exclude = {"reports", "employees", "weapons", "plates", "equipments"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Organization extends AuditModel {
    private String name;
    private String address;
    private State state;
    private City city;

    private int bulletsQuantity;

    @DBRef
    private LinkedList<Report> reports;

    @DBRef
    private LinkedList<Employee> employees;

    @DBRef
    private LinkedList<Weapon> weapons;

    @DBRef
    private LinkedList<Plate> plates;

    @DBRef
    private LinkedList<Equipment> equipments;
}
