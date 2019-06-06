package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
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
public class Request extends AuditModel {
    @GraphQLNonNull
    private Organization organization;

    @GraphQLNonNull
    private String title;

    @GraphQLNonNull
    private Image image;

    @GraphQLNonNull
    private Date dateOnImage;

    private String description;
}
