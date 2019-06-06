package com.company.paw.graphql.InputTypes;

import com.company.paw.models.enums.WeaponCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeaponInfoInput {
    private String type;
    private WeaponCategory category;
}
