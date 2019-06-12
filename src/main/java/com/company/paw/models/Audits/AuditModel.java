package com.company.paw.models.Audits;

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

//    @Temporal(TemporalType.TIMESTAMP)
//    @CreatedDate
//    private Date created;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedDate
//    private Date updated;

    private boolean verified = true;
}
