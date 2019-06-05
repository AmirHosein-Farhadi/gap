package com.company.paw.graphql.InputTypes;

import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import com.company.paw.models.Request;
import com.company.paw.models.WeaponInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    private String serial;
    private Request request;
    private Organization organization;
    private Employee currentUser;
    private boolean isPrivate;
    private WeaponInfo info;
}
