package com.awslambda.apiregistry.dto;

import lombok.Data;

import java.util.Date;


@Data
public class CustomerDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private Date createdOn = new Date();
    private boolean deleted = false;
}
