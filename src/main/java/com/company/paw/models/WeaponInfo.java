package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import com.company.paw.models.enums.WeaponCategory;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Document
@GraphQLType
public class WeaponInfo extends AuditModel {
    @GraphQLNonNull
    private String type;

    @GraphQLNonNull
    private WeaponCategory category;
}
