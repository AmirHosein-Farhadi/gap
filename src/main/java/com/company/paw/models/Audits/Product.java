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
@GraphQLInterface(name = "Product")
public abstract class Product extends AuditModel {
    @GraphQLNonNull
    private String serial;

    private Request request;

    @GraphQLNonNull
    private Organization organization;

    private List<Record> records;
    private List<Image> images;
    private Employee currentUser;
}
