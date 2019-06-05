package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
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
    private String name;
    private String nationalId;
    private String employeeId;
    private String address;
    private String phoneNumber;
    private Position position;
    private Organization organization;
    private List<String> images;
}
