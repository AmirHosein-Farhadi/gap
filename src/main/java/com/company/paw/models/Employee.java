package com.company.paw.models;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"images"})
@AllArgsConstructor
@Document
@GraphQLType
public class Employee {
    @Id
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    @LastModifiedDate
    private Date updated;

    @Column(name = "verified", nullable = false)
    private boolean verified = true;

    public Employee(String name, String nationalId, String employeeId, String address, String phoneNumber, boolean isManager) {
        this.name = name;
        this.nationalId = nationalId;
        this.employeeId = employeeId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isManager = isManager;
    }

    private String name;
    private String nationalId;
    private String employeeId;
    private String address;
    private String phoneNumber;
    private Position position;
    private Organization organization;
    private List<String> images;
    private boolean isManager;
}
