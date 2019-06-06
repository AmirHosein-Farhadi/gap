package com.company.paw.models.Audits;

import com.company.paw.models.*;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"images"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@GraphQLInterface(name = "AuditModel")
public abstract class Product extends AuditModel {
    @GraphQLNonNull
    private String serial;

    @GraphQLNonNull
    private Request request;

    private List<Record> records;

    @GraphQLNonNull
    private Organization organization;

    private List<Image> images;
    private Employee currentUser;
}
