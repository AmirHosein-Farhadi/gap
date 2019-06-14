package com.company.paw.models;

import com.company.paw.models.audits.AuditModel;
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
public class Request extends AuditModel {
    private Employee employee;
    private Organization organization;
    private String title;
    private Image image;
    private Date dateOnImage;
    private String description;
    private Report report;
}
