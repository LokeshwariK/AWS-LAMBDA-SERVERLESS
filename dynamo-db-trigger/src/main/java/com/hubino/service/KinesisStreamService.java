package com.hubino.service;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordsRequest;
import com.amazonaws.services.kinesis.model.PutRecordsRequestEntry;
import com.amazonaws.services.kinesis.model.PutRecordsResult;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.hubino.config.AmazonKinesisConfig;
import com.hubino.constants.Constants;

public class KinesisStreamService {

	static final String KINESIS_STREAM_NAME = "data";

	static AmazonKinesis kinesisClient;
	static LambdaLogger lambdaLogger;

	public KinesisStreamService() {
		kinesisClient = AmazonKinesisConfig.amazonKinesisConfig();
	}

	/**
	 * Get the logger from handler
	 * 
	 * @param lambdaLogger
	 */
	public void setLambdaLogger(LambdaLogger lambdaLogger) {
		KinesisStreamService.lambdaLogger = lambdaLogger;
	}

	/**
	 * Stream the log as string
	 * 
	 * @param log
	 */
	public void streamLogs(String log) {
		List<String> data = new ArrayList<String>();
		data.add(log);
		streamLogs(data);
	}

	/**
	 * Stream the list of data
	 * 
	 * @param data
	 */
	public void streamLogs(List<String> data) {
		PutRecordsRequest recordsRequest = new PutRecordsRequest();
		recordsRequest.setStreamName(KINESIS_STREAM_NAME);
		recordsRequest.setRecords(getRecordsRequestList(data));
		PutRecordsResult results = kinesisClient.putRecords(recordsRequest);
		if (results.getFailedRecordCount() > 0) {
			lambdaLogger.log(Constants.ERROR + Constants.SPACE + results.getFailedRecordCount());
		} else {
			lambdaLogger.log(data.toString());
		}
	}

	/**
	 * Get the RecordsRequestList
	 * 
	 * @param data
	 * @return
	 */
	private List<PutRecordsRequestEntry> getRecordsRequestList(List<String> data) {
		List<PutRecordsRequestEntry> putRecordsRequestEntries = new ArrayList<PutRecordsRequestEntry>();

		for (String log : data) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			log = formatter.format(date) + Constants.SPACE + log + Constants.NEXT_LINE;
			PutRecordsRequestEntry requestEntry = new PutRecordsRequestEntry();
			requestEntry.setData(ByteBuffer.wrap(log.getBytes()));
			requestEntry.setPartitionKey(UUID.randomUUID().toString());
			putRecordsRequestEntries.add(requestEntry);
		}
		return putRecordsRequestEntries;
	}

}
