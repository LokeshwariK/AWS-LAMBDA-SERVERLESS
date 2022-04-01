package com.awslambda.apiregistry.dao;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.awslambda.apiregistry.dto.CustomerDTO;
import com.awslambda.apiregistry.dto.SearchRequestDTO;
import com.awslambda.apiregistry.model.Customer;
import com.awslambda.apiregistry.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CustomerDAO extends Constants {

    private static final Logger loggers = LoggerFactory.getLogger(CustomerDAO.class);
    private Connection connection;
    private Context context;

    public CustomerDAO(Context context) {
        this.context = context;
        initDB();
    }

    public CustomerDAO(Logger logger, Context context) {
        initDB();
    }

    private void initDB() {
        LambdaLogger logger = context.getLogger();
        try {
            logger.log(" CustomerDAO ....initDB............ ");
            String host = "aws-serverless-db.chow8ukdjwgu.ap-south-1.rds.amazonaws.com";
            int port = 3306;
            String url = "jdbc:mysql://" + host + ":" + port;
            String username = "admin";
            String password = "Hubino!123";
            String database = "aws-db";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            logger.log("CustomerDAO initDB end ");
        } catch (Exception e) {
            logger.log("CustomerDAO initDB Exception "
                    + Arrays.toString(e.getStackTrace()));
        }
    }

    public List<Customer> getAllCustomers() {
        LambdaLogger logger = context.getLogger();
        ResultSet resultSet = null;
        List<Customer> customers = new ArrayList<>();
        logger.log("Invoking connection");
        try (Statement statement = connection.createStatement()) {
            logger.log("Executing Query.........");
            statement.executeUpdate("use `aws-db`");
            resultSet = statement.executeQuery(String.format(QUERY_GET_ALL_CUSTOMERS));
            while (resultSet.next()) {
                Customer customer = new Customer();
                logger.log("Setting Customer details");
                customer.setId(resultSet.getInt("id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setMobileNumber(resultSet.getString("mobile_number"));
                customer.setDeleted(resultSet.getBoolean("is_deleted"));
                customer.setCreatedOn(resultSet.getDate("created_on"));
                customers.add(customer);
            }
            logger.log("Returning customer details");
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
            logger.log("Error in getting customer details..........." + e.getMessage());
            return customers;
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (Exception e) {
                logger.log("Exception" + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public Customer getCustomerById(Integer id) {
        LambdaLogger logger = context.getLogger();
        ResultSet resultSet = null;
        Customer customer = null;
        logger.log("Invoking connection");
        try (Statement statement = connection.createStatement()) {
            logger.log("Executing Query.........");
            statement.executeUpdate("use `aws-db`");
            resultSet = statement.executeQuery(String.format(QUERY_GET_CUSTOMER_BY_ID, id));
            while (resultSet.next()) {
                customer = new Customer();
                logger.log("Setting Customer details");
                customer.setId(resultSet.getInt("id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setMobileNumber(resultSet.getString("mobile_number"));
                customer.setDeleted(resultSet.getBoolean("is_deleted"));
                customer.setCreatedOn(resultSet.getDate("created_on"));
            }
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
            logger.log("Error in getting customer details..........." + e.getMessage());
            return null;
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (Exception e) {
                logger.log("Exception" + Arrays.toString(e.getStackTrace()));
            }
        }
    }
    public List<Customer> search(SearchRequestDTO searchRequestDTO) {
        LambdaLogger logger = context.getLogger();
        ResultSet resultSet = null;
        List<Customer> customers = new ArrayList<>();
        logger.log("Invoking connection");
        try (Statement statement = connection.createStatement()) {
            logger.log("Executing Query.........");
            statement.executeUpdate("use `aws-db`");
            resultSet = statement.executeQuery(String.format(QUERY_SEARCH_CUSTOMERS,searchRequestDTO.getMobileNumber(),searchRequestDTO.getEmail()));
            while (resultSet.next()) {
                Customer customer = new Customer();
                logger.log("Setting Customer details");
                customer.setId(resultSet.getInt("id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setMobileNumber(resultSet.getString("mobile_number"));
                customer.setDeleted(resultSet.getBoolean("is_deleted"));
                customer.setCreatedOn(resultSet.getDate("created_on"));
                customers.add(customer);
            }
            logger.log("Returning customer details");
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
            logger.log("Error in getting customer details..........." + e.getMessage());
            return customers;
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (Exception e) {
                logger.log("Exception" + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public void insertCustomer(CustomerDTO customerDTO) {
        LambdaLogger logger = context.getLogger();
        logger.log("Invoking connection");
        try (Statement statement = connection.createStatement()) {
            logger.log("Executing Query.........");
            statement.executeUpdate("use `aws-db`");
            String createdOn = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            logger.log(String.format(Constants.QUERY_INSERT_CUSTOMER, customerDTO.getFirstName(),
                    customerDTO.getLastName(), customerDTO.getEmail(), customerDTO.getMobileNumber(),createdOn,0));
            statement.executeUpdate(String.format(Constants.QUERY_INSERT_CUSTOMER, customerDTO.getFirstName(),
                    customerDTO.getLastName(), customerDTO.getEmail(), customerDTO.getMobileNumber(), createdOn,0));
            logger.log("customer details inserted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log("Error in inserting customer details..........." + e.getMessage());
        }
    }

}
