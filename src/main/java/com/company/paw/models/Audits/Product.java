package com.company.paw.models.Audits;

import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import com.company.paw.models.Record;
import com.company.paw.models.Request;
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
    private String serial;
    private Request request;
    private List<Record> records;
    private Organization organization;
    private List<String> images;
    private Employee currentUser;
}
