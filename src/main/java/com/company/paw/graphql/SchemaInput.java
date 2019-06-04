package com.company.paw.graphql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchemaInput {
    private String query;
    private String operationName;
    private Variables variables;

    @JsonProperty("variables")
    public void setVariables(Variables variables) {
        this.variables = variables;
    }
}
