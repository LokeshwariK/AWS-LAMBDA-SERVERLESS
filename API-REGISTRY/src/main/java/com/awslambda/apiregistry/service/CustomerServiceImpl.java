package com.awslambda.apiregistry.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.awslambda.apiregistry.dao.CustomerDAO;
import com.awslambda.apiregistry.dto.CustomerDTO;
import com.awslambda.apiregistry.dto.SearchRequestDTO;
import com.awslambda.apiregistry.model.Customer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDAO customerDAO;

    public CustomerServiceImpl(Context context) {
        customerDAO=new CustomerDAO(context);
    }

    /**
     * This method is used to Get all Customers
     * @return
     * @throws SQLException
     */
    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = customerDAO.getAllCustomers();
        if (customers == null || customers.size() <= 0) {
            return new ArrayList<>();
        }
        return customers;
    }

    /**
     * This method is used to Get Customer By Id
     * @param id
     * @return
     */
    @Override
    public Customer getCustomerById(Integer id) {
        return customerDAO.getCustomerById(id);
    }

    /**
     * This method is used to search the customer details
     * @param searchRequestDTO
     * @return
     */
    @Override
    public List<Customer> search(SearchRequestDTO searchRequestDTO) {
        List<Customer> customers = customerDAO.search(searchRequestDTO);
        if (customers == null || customers.size() <= 0) {
            return new ArrayList<>();
        }
        return customers;
    }

    @Override
    public void insertCustomer(CustomerDTO customerDTO) {
        customerDAO.insertCustomer(customerDTO);
    }
}
