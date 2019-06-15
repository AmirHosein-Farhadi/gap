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
    private String currentUserId;
    private boolean status;

    private String weaponTypeId;            //weapon specific
    private String weaponName;
    private String weaponCardImageId;
    private String weaponCardNumber;
    private String weaponCardExpirationDate;
}
