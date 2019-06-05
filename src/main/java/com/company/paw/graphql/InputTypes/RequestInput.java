package com.company.paw.graphql.InputTypes;

import com.company.paw.models.Organization;

import java.util.Date;

public class RequestInput {
    private Organization organization;
    private String title;
    private String image;
    private Date dateOnImage;
    private String description;
}
