package com.awslambda.apiregistry.service;

import com.awslambda.apiregistry.dto.CustomerDTO;
import com.awslambda.apiregistry.dto.SearchRequestDTO;
import com.awslambda.apiregistry.model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers() throws SQLException;

    Customer getCustomerById(Integer id);

    List<Customer> search(SearchRequestDTO searchRequestDTO);

    void insertCustomer(CustomerDTO customerDTO);


}
