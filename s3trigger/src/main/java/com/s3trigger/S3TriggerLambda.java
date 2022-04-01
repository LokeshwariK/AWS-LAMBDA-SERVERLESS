package com.s3trigger;

import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.s3trigger.entity.Person;
import com.s3trigger.service.PersonService;

public class S3TriggerLambda implements RequestHandler<Map<String, Object>, Map<String, Object>> {
	private static PersonService personService;

	public S3TriggerLambda() {
		personService = new PersonService();
	}

	public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("S3 triggered into lambda function!");
		JSONObject s3Details = personService.getS3Details(input);
		String s3BucketName = s3Details.get("BucketName").toString();
		String s3FileName = s3Details.get("FileName").toString();
		logger.log("BucketName=" + s3BucketName);
		logger.log("FileName=" + s3FileName);
		try {
			byte[] content = personService.readFile(s3BucketName, s3FileName);
			logger.log("Got content from " + s3FileName);
			Person person = personService.addContentToPerson(content);
			logger.log(s3FileName + " data added to Person Entity");
			logger.log("Person Details:" + person);
			personService.StoreDataToDynamoDB(person);
			logger.log("Successfully stored data of " + s3FileName + " to DynamoDB");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

}
