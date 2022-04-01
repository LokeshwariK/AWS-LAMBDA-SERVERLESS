package com.awslambda.apiregistry.dto;

import lombok.Data;

@Data
public class SearchRequestDTO {
    private String email;
    private String mobileNumber;
}
