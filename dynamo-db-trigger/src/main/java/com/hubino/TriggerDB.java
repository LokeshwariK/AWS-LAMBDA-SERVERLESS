package com.hubino;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.hubino.constants.Constants;
import com.hubino.service.KinesisStreamService;
import com.hubino.service.SimpleEmailService;

public class TriggerDB implements RequestHandler<DynamodbEvent, String> {

	static SimpleEmailService sEmailService;
	static KinesisStreamService kStreamService;

	/**
	 * Constructor to initialize sEmailService and kStreamService
	 */
	public TriggerDB() {
		sEmailService = new SimpleEmailService();
		kStreamService = new KinesisStreamService();
	}

	/**
	 * Handler for the incoming DynamodbEvent
	 */
	public String handleRequest(DynamodbEvent event, Context context) {
		kStreamService.setLambdaLogger(context.getLogger());

		// Stream a string value directly
		kStreamService.streamLogs(Constants.TRIGGER_DB);

		// Get the dynamo db events
		List<String> loggerList = new ArrayList<String>();
		for (DynamodbStreamRecord record : event.getRecords()) {
			loggerList.add(record.getEventID() + Constants.SPACE + record.getEventName());
			loggerList.add(record.getDynamodb().toString());
		}

		// Send dynamo db event to mail
		sEmailService.sendMail(Constants.DEFAULT_MAIL_SUBJECT, loggerList.toString());

		// Stream a list of string
		kStreamService.streamLogs(loggerList);

		return Constants.TRIGGER_DB;
	}

}
