package com.company.paw.graphql.InputTypes;

import com.company.paw.models.Image;
import com.company.paw.models.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestInput {
    private Organization organization;
    private String title;
    private Image image;
    private Date dateOnImage;
    private String description;
}
