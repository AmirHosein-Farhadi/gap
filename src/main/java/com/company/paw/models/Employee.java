package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"images"},callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Employee extends AuditModel {
    @GraphQLNonNull
    private String fullName;

    @GraphQLNonNull
    private String nationalId;

    @GraphQLNonNull
    private String employeeId;

    @GraphQLNonNull
    private Position position;

    @GraphQLNonNull
    private Organization organization;

    private String address;
    private String phoneNumber;
    private List<Image> images;
}
