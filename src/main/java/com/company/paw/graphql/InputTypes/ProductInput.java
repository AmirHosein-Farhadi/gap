package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    private String serial;

    private String organizationId;
    private List<String> currentUsersId;
    private List<String> reportsId;
    private String imageId;

    private String mappedPlateId;           //plate specific
    private boolean isPrivate;

    private String weaponTypeId;            //weapon specific
    private String name;
}
