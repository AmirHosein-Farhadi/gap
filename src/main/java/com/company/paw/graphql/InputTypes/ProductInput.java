package com.company.paw.graphql.InputTypes;

import com.company.paw.models.WeaponCategory;
import com.company.paw.models.WeaponType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    private String serial;
    private String requestId;
    private String organizationId;
    private String currentUserId;
    private List<String> recordsId;
    private List<String> imagesId;

    //plate specific
    private boolean isPrivate;
    private String mappedPlateId;

    //weapon specific
    private WeaponType weaponTypeId;
    private WeaponCategory weaponCategoryId;
}
