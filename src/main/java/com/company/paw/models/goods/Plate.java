package com.company.paw.models.goods;

import com.company.paw.models.Image;
import com.company.paw.models.audits.Product;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class Plate extends Product {
    @DBRef
    private Plate mappedPlate;

    private int plateStatus;
    private boolean isPrivate;
    private Image minority;
}
