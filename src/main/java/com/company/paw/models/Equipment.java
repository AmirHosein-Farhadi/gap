package com.company.paw.models;

import com.company.paw.models.audits.Product;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Equipment extends Product {
    int type; //1 for spray,    2 for shocker,  3 for talkie.
    private Date weaponCardExpirationDate;
    private Image weaponCardImage;
    private String weaponCardNumber;
}
