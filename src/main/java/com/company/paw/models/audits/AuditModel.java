package com.company.paw.models.audits;

import io.leangen.graphql.annotations.GraphQLId;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.Data;

import javax.persistence.Id;

@Data
@GraphQLInterface(name = "AuditModel")
public abstract class AuditModel {
    @Id
    @GraphQLId
    private String id;
    private boolean verified = true;
}
