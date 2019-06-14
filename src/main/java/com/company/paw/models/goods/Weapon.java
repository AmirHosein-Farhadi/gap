package com.company.paw.models.goods;

import com.company.paw.models.Image;
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
public class Weapon extends Product {
    private String name;
    private WeaponType type;

    private Date weaponCardExpirationDate;
    private Image weaponCardImage;
    private String weaponCardNumber;
//    private boolean isSerializeAble;
//    private int quantity;
}
