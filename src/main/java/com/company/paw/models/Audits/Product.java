package com.company.paw.models.Audits;

import com.company.paw.models.*;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"reports", "currentUsers"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@GraphQLInterface(name = "Product")
public abstract class Product extends AuditModel {
    private String serial;
    private String productNumber;
    private boolean status;
    private Organization organization;
    private Employee currentUser;
    private Image image;

    @DBRef
    private List<Report> reports;
}
