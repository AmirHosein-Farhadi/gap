package com.company.paw.models;

import com.company.paw.models.audits.AuditModel;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"cities"}, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class State extends AuditModel {
    private String name;

    @DBRef
    private LinkedList<City> cities;

    public void addSubCity(City city) {
        LinkedList<City> cities = this.cities;
        cities.add(city);
        this.cities = cities;
    }
}