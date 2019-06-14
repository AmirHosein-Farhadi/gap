package com.company.paw.models;

import com.company.paw.models.audits.AuditModel;
import com.company.paw.models.goods.Equipment;
import com.company.paw.models.goods.Plate;
import com.company.paw.models.goods.Weapon;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"plates", "reports", "weapons"}, callSuper = false)
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

    private Date employeeCardExpirationDate;
    private Image employeeCardImage;
    private String employeeCardNumber;

    private Equipment spray;
    private Equipment shocker;
    private Equipment talkie;
    private int bullets;

    @DBRef
    private List<Report> reports;

    @DBRef
    private List<Weapon> weapons;

    @DBRef
    private List<Plate> plates;
}
