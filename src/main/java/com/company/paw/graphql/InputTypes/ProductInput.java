package com.company.paw.graphql.InputTypes;

import com.company.paw.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInput {
    private String serial;
    private Request request;
    private Organization organization;
    private Employee currentUser;
    private List<Record> records;
    private List<Image> images;

    //plate specific
    private boolean isPrivate;
    private Plate mappedPlate;

    //weapon specific
    private WeaponInfo info;
}
