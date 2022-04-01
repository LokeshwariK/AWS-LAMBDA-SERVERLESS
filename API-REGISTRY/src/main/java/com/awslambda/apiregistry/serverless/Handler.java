package com.awslambda.apiregistry.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.awslambda.apiregistry.dto.CustomerDTO;
import com.awslambda.apiregistry.dto.SearchRequestDTO;
import com.awslambda.apiregistry.model.Customer;
import com.awslambda.apiregistry.service.CustomerService;
import com.awslambda.apiregistry.service.CustomerServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Handler implements RequestHandler<Object, ResponseEntity> {

    private CustomerService customerService;


    /**
     * This method is used to return simple message
     *
     * @param o contains object
     * @param context
     * @return
     */
    @Override
    public ResponseEntity<?> handleRequest(Object o, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Handler Initiated.........");
        customerService = new CustomerServiceImpl(context);
        JSONObject jsonObject = new JSONObject();
        try {
            List<Customer> customers = customerService.getAllCustomers();
            logger.log("No.of customer details found...." + customers.size());
            List<CustomerDTO> customerDTOS = new ArrayList<>();
            if (customers.size() <= 0) {
                jsonObject.put("status", "Error");
                jsonObject.put("statusCode", 400);
                jsonObject.put("info", "No customer details found");
            }
            for (Customer customer : customers) {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setId(customer.getId());
                customerDTO.setFirstName(customer.getFirstName());
                customerDTO.setLastName(customer.getLastName());
                customerDTO.setEmail(customer.getEmail());
                customerDTO.setMobileNumber(customer.getMobileNumber());
                customerDTO.setDeleted(customer.isDeleted());
                customerDTO.setCreatedOn(customer.getCreatedOn());
                customerDTOS.add(customerDTO);
            }

            jsonObject.put("status", "Success");
            jsonObject.put("statusCode", 200);
            jsonObject.put("info", customerDTOS);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(jsonObject.toMap());
    }

    /**
     * This method is used to get customer by id
     * @param id
     * @param context
     * @return
     */
    public ResponseEntity<?> handleRequestById(Integer id, Context context) {
        context.getLogger().log("HandleRequestById Initiated........." + id);
        JSONObject jsonObject = new JSONObject();
        customerService = new CustomerServiceImpl(context);
        Customer customer = customerService.getCustomerById(id);
        CustomerDTO customerDTO = new CustomerDTO();
        if (customer == null) {
            jsonObject.put("status", "Error");
            jsonObject.put("statusCode", 400);
            jsonObject.put("info", "No customer details found for id" + id);
        } else {
            BeanUtils.copyProperties(customer, customerDTO);
            jsonObject.put("status", "Success");
            jsonObject.put("statusCode", 200);
            jsonObject.put("info", customerDTO);
        }

        return ResponseEntity.ok(jsonObject.toMap());
    }

    /**
     * This method is used to search the customer details
     * @param searchRequestDTO
     * @param context
     * @return
     */

    public ResponseEntity<?> handleSearchRequest(SearchRequestDTO searchRequestDTO, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Handler Initiated.........");
        customerService = new CustomerServiceImpl(context);
        JSONObject jsonObject = new JSONObject();
        try {
            if ((searchRequestDTO.getEmail() == null || searchRequestDTO.getEmail().isEmpty()) || searchRequestDTO.getMobileNumber() == null || searchRequestDTO.getMobileNumber().isEmpty()) {
                jsonObject.put("status", "Error");
                jsonObject.put("statusCode", 400);
                jsonObject.put("info", "Parameter should not be empty");
                return ResponseEntity.ok(jsonObject.toMap());
            }
            List<Customer> customers = customerService.search(searchRequestDTO);
            logger.log("No.of customer details found...." + customers.size());
            List<CustomerDTO> customerDTOS = new ArrayList<>();
            if (customers.size() <= 0) {
                jsonObject.put("status", "Error");
                jsonObject.put("statusCode", 400);
                jsonObject.put("info", "No customer details found");
                return ResponseEntity.ok(jsonObject.toMap());
            }
            for (Customer customer : customers) {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setId(customer.getId());
                customerDTO.setFirstName(customer.getFirstName());
                customerDTO.setLastName(customer.getLastName());
                customerDTO.setEmail(customer.getEmail());
                customerDTO.setMobileNumber(customer.getMobileNumber());
                customerDTO.setDeleted(customer.isDeleted());
                customerDTO.setCreatedOn(customer.getCreatedOn());
                customerDTOS.add(customerDTO);
            }


            jsonObject.put("status", "Success");
            jsonObject.put("statusCode", 200);
            jsonObject.put("info", customerDTOS);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(jsonObject.toMap());

    }

    public ResponseEntity<?> insertCustomer(CustomerDTO customerDTO, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Handler Initiated.........");
        customerService = new CustomerServiceImpl(context);
        JSONObject jsonObject = new JSONObject();
        if (customerDTO == null) {
            jsonObject.put("status", "Error");
            jsonObject.put("statusCode", 400);
            jsonObject.put("info", "Parameter should not be empty");
            return ResponseEntity.ok(jsonObject.toMap());
        }

        customerService.insertCustomer(customerDTO);
        jsonObject.put("status", "Success");
        jsonObject.put("statusCode", 200);
        jsonObject.put("info", "Customer details inserted successfully");
        return ResponseEntity.ok(jsonObject.toMap());
    }
}
