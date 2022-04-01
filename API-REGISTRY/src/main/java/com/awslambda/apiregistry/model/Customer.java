package com.awslambda.apiregistry.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CUSTOMERS")
public class Customer extends BaseEntity {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

}
