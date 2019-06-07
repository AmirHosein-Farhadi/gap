package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestInput {
    private String organizationId;
    private String titleId;
    private String imageId;
    private String dateOnImage;
    private String description;
}
