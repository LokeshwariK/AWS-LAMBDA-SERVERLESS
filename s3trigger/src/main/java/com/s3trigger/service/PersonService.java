package com.s3trigger.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.s3trigger.config.DynamoDBConfig;
import com.s3trigger.config.S3Config;
import com.s3trigger.entity.Person;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonService {
	private static DynamoDBMapper mapper;
	private static AmazonS3 amazonS3;

	public PersonService() {
		mapper = new DynamoDBMapper(new DynamoDBConfig().amazonDynamoDBConfig());
		amazonS3 = new S3Config().amazonS3Config();
	}

	public JSONObject getS3Details(Map<String, Object> input) {
		JSONObject s3Details = new JSONObject();
		Object recordsObject = input.get("Records");
		ArrayList<LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>> arrList = (ArrayList<LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>>>) recordsObject;
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String>>> lhMap = arrList.get(0);
		LinkedHashMap<String, LinkedHashMap<String, String>> s3Map = (LinkedHashMap<String, LinkedHashMap<String, String>>) lhMap
				.get("s3");
		LinkedHashMap<String, String> bucketMap = s3Map.get("bucket");
		LinkedHashMap<String, String> s3ObjMap = s3Map.get("object");
		String s3BucketName = bucketMap.get("name").toString();
		String s3FileName = s3ObjMap.get("key").toString();
		s3Details.put("BucketName", s3BucketName);
		s3Details.put("FileName", s3FileName);
		return s3Details;
	}

	public byte[] readFile(String bucketName, String fileName) throws IOException {
		S3Object s3Object = amazonS3.getObject(bucketName, fileName);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		byte[] content = IOUtils.toByteArray(inputStream);
		return content;
	}

	public Person addContentToPerson(byte[] content) {
		Person person = new Person();
		JSONObject personJSON = new JSONObject(new String(content));
		person.setPersonId(personJSON.getString("personId"));
		person.setName(personJSON.getString("name"));
		person.setAge(personJSON.getInt("age"));
		person.setCar(personJSON.getString("car"));
		person.setOccupation(personJSON.getString("occupation"));
		return person;
	}

	public void StoreDataToDynamoDB(Person person) {
		mapper.save(person);
	}

}
