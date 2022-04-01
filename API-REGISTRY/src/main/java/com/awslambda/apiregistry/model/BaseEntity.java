package com.awslambda.apiregistry.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "CREATED_ON")
    private Date createdOn = new Date();

    @Column(name = "IS_DELETED")
    private boolean deleted = false;

}
