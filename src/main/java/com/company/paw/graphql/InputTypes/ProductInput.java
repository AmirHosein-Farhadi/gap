package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    private String serial;
    private String organizationId;
    private String imageId;

    private String minorityId;
    private boolean isPrivate;

    private String weaponTypeId;            //weapon specific
    private String weaponName;

//    private boolean isSerializeAble;
//    private int amount;
}
