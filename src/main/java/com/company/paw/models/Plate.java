package com.company.paw.models;

import com.company.paw.models.Audits.Product;
import io.leangen.graphql.annotations.GraphQLNonNull;

public class Plate extends Product {
    @GraphQLNonNull
    private boolean isPrivate;
    private Plate mappedPlate;
}
