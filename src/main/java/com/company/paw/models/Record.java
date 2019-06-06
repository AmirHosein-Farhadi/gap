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
public class Record extends AuditModel {
    @GraphQLNonNull
    private Employee user;

    @GraphQLNonNull
    private Product product;

    @GraphQLNonNull
    private Organization organization;

    @GraphQLNonNull
    private Date time;

    @GraphQLNonNull
    private boolean status;

    @GraphQLNonNull
    private boolean isReturning;

    private String description;
}
