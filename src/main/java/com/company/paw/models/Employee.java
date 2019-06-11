package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"images", "reports", "weapons"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Employee extends AuditModel {
    private String fullName;
    private Date birthDate;
    private Position position;
    private String nationalId;
    private Organization organization;
    private String address;
    private String phoneNumber;
    private Image image;
    private Weapon spray;
    private Weapon shocker;
    private Weapon bisim;

    @DBRef
    private List<Report> reports;

    @DBRef
    private List<Weapon> weapons;

    @DBRef
    private List<Plate> plates;
}
