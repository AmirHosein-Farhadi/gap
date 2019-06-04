package com.company.paw.models;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"images"})
@AllArgsConstructor
@Document
@GraphQLType
public class Product {
    @Id
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

    private String serial;
    private ProductInfo productInfo;
    private Organization organization;
    private Employee currentUser;
    private List<String> images;
    private boolean isAvailable;
}
