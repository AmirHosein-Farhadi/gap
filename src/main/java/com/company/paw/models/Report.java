package com.company.paw.models;

import com.company.paw.models.audits.AuditModel;
import com.company.paw.models.audits.Product;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
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
    private Product product;
    private Weapon weapon;
    private Plate plate;
    private Employee employee;
    private Organization organization;
    private Request request;

    private Date borrowTime;
    private Date returnTime;
    private boolean borrowStatus;
    private boolean returnStatus;
    private String borrowDescription;
    private String returnDescription;

    private Image acceptImage;
    private Image reciteImage;
    private Image informationLetter;
    private Image armyLetter;
}
