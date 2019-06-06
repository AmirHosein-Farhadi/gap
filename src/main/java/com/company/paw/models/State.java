package com.company.paw.models;

import com.company.paw.models.Audits.AuditModel;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@GraphQLType
public class State extends AuditModel {
    private String name;
    private List<City> cities = new ArrayList<>();

    public void addSubCity(City city) {
        this.cities.add(city);
    }
}
