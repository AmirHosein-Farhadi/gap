package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    //    @GraphQLNonNull
    private String fullName;

    //    @GraphQLNonNull
    private String nationalId;

    //    @GraphQLNonNull
    private String employeeId;

    //    @GraphQLNonNull
    private Position position;

    //    @GraphQLNonNull
    private Organization organization;

    @DBRef
    private List<Image> images;

    private String address;
    private String phoneNumber;
}
