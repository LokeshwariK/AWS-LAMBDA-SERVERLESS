package com.s3trigger.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class S3Config {
	public AmazonS3 amazonS3Config() {
		AWSCredentials credentials = new BasicAWSCredentials(System.getenv("ACCESS_KEY"), System.getenv("SECRET_KEY"));
		return AmazonS3Client.builder().withRegion(System.getenv("REGION"))
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
	}
}
