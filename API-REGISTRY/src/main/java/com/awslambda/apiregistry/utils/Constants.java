package com.awslambda.apiregistry.utils;

public class Constants {

    public static final String QUERY_STRING = "%s";

    public static final String QUERY_GET_ALL_CUSTOMERS = "select * from customers";
    public static final String QUERY_GET_CUSTOMER_BY_ID = "select * from customers where id = " + QUERY_STRING ;
    public static final String QUERY_SEARCH_CUSTOMERS = "select * from customers where mobile_number='" + QUERY_STRING + "' and email='" + QUERY_STRING + "'" ;
    public static final String QUERY_INSERT_CUSTOMER = "INSERT INTO customers(first_name, last_name, email, mobile_number, created_on,is_deleted) VALUES(" +
            "'" + QUERY_STRING + "'," +
            "'" + QUERY_STRING + "'," +
            "'" + QUERY_STRING + "'," +
            "'" + QUERY_STRING + "'," +
            "'" + QUERY_STRING + "'," +
            "" + QUERY_STRING +")";

}
