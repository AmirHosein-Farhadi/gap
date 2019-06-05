package com.company.paw.models.Audits;

import io.leangen.graphql.annotations.GraphQLId;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@GraphQLInterface(name = "AuditModel")
public abstract class AuditModel {
    @Id
    @GraphQLId
    @GraphQLNonNull
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
}
