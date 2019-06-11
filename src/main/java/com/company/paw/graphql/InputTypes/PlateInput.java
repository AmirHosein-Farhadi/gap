package com.company.paw.graphql.InputTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateInput {
    private String privatePlateSerial;
    private int privatePlateStatus;
    private String minorityImage;
    private String backupPlateSerial;
    private int backupPlateStatus;
    private String organizationId;
}
