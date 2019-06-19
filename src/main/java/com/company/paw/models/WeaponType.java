package com.company.paw.models;

import com.company.paw.models.audits.AuditModel;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Data
@EqualsAndHashCode(exclude = {"weaponNames"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class WeaponType extends AuditModel {
    private String name;

    @DBRef
    private LinkedList<WeaponName> weaponNames;
}