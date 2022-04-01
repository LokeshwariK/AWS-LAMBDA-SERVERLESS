package com.s3trigger.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "person")
public class Person implements Serializable {
	@DynamoDBHashKey(attributeName = "personId")
	private String personId;
	@DynamoDBAttribute
	private String name;
	@DynamoDBAttribute
	private int age;
	@DynamoDBAttribute
	private String occupation;
	@DynamoDBAttribute
	private String car;
}
