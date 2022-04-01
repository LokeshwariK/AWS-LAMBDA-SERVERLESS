package com.s3trigger.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDBConfig {

	public static final String SERVICE_ENDPOINT = "dynamodb.ap-south-1.amazonaws.com";

	public AmazonDynamoDB amazonDynamoDBConfig() {
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(
						new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, System.getenv("REGION")))
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(System.getenv("ACCESS_KEY"), System.getenv("SECRET_KEY"))))
				.build();
	}
}
