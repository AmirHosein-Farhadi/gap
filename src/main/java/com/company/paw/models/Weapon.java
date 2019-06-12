package com.company.paw.models;

import com.company.paw.models.Audits.Product;
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
public class Weapon extends Product {
    private String name;
    private Date weaponCardExpirationDate;
    private Image weaponCardImage;
    private String weaponCardNumber;
//    private boolean isSerializeAble;
//    private int quantity;
}
