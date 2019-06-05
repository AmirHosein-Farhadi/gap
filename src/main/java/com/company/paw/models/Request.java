package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Document
@GraphQLType
public class Request extends AuditModel {
    private Organization organization;
    private String title;
    private String image;
    private String description;
    private Date dateOnImage;
}
