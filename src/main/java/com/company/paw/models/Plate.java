package com.company.paw.models;

import com.company.paw.models.audits.Product;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

@Data
@EqualsAndHashCode(exclude = {"minority"},callSuper = false)
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
    private LinkedList<Image> minority;
}
