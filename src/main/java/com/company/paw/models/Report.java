package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import com.company.paw.models.Audits.Product;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Report extends AuditModel {
    private Employee employee;
    private Organization organization;
    private Product product;
    private Date borrowTime;
    private Date returnTime;
    private Request request;
    private boolean borrowStatus;
    private boolean returnStatus;
    private String borrowDescription;
    private String returnDescription;
    private Image acceptImage;
    private Image reciteImage;
    private Image informationLetter;
    private Image armyLetter;
}
