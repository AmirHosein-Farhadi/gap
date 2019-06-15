package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateInput {
    private String serial;
    private String organizationId;
    private String currentUserId;
    private List<String> minorityImagesId;
    private int plateStatus;
    private boolean isPrivate;
    private boolean status;
}
