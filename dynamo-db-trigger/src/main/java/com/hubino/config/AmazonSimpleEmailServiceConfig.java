package com.hubino.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

public class AmazonSimpleEmailServiceConfig {

	public static final String ACCESS_KEY = System.getenv("ACCESS_KEY");
	public static final String SECRET_KEY = System.getenv("SECRET_KEY");

	/**
	 * Configure AmazonSimpleEmailService
	 * 
	 * @return
	 */
	public AmazonSimpleEmailService amazonSimpleEmailServiceConfig() {
		return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
				.build();
	}
}