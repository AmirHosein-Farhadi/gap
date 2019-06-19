package com.company.paw.models;

import com.company.paw.models.audits.AuditModel;
import com.company.paw.models.audits.Product;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(exclude = {"employee", "organization", "product", "request"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Report extends AuditModel {
    @DBRef
    private Product product;

    @DBRef
    private Employee employee;

    @DBRef
    private Organization organization;

    private Date borrowTime;
    private Date returnTime;
    private Request request;
    private boolean borrowStatus;
    private boolean returnStatus;
    private String borrowDescription;
    private String returnDescription;

    @DBRef
    private Image acceptImage;

    @DBRef
    private Image reciteImage;

    @DBRef
    private Image informationLetter;

    @DBRef
    private Image armyLetter;
}
