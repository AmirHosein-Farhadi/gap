package com.company.paw.models;

import com.company.paw.models.audits.AuditModel;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedList;

@Data
@EqualsAndHashCode(exclude = {"plates", "reports", "weapons","requests"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Employee extends AuditModel {
    private String fullName;
    private Date birthDate;
    private String address;
    private String phoneNumber;
    private String nationalId;
    private String employeeCardNumber;
    private Date employeeCardExpirationDate;

    private Image employeeCardImage;
    private Position position;
    private Organization organization;
    private Image image;

    private Equipment spray;
    private Equipment shocker;
    private Equipment talkie;
    private int bullets;

    @DBRef
    private LinkedList<Report> reports;

    @DBRef
    private LinkedList<Request> requests;

    @DBRef
    private LinkedList<Weapon> weapons;

    @DBRef
    private LinkedList<Plate> plates;
}
