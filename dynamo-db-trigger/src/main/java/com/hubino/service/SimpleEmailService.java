package com.hubino.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.hubino.config.AmazonSimpleEmailServiceConfig;
import com.hubino.constants.Constants;

public class SimpleEmailService {

	static KinesisStreamService kStreamService;

	static final String FROM = "srikannan2510@gmail.com";
	static final String TO = "kannan25101997@gmail.com";

	/**
	 * Constructor to initialize kStreamService
	 */
	public SimpleEmailService() {
		kStreamService = new KinesisStreamService();
	}

	/**
	 * Send an Email
	 * 
	 * @param subject
	 * @param textBody
	 */
	public void sendMail(String subject, String textBody) {
		try {
			AmazonSimpleEmailService client = new AmazonSimpleEmailServiceConfig().amazonSimpleEmailServiceConfig();

			SendEmailRequest req = new SendEmailRequest().withDestination(new Destination().withToAddresses(TO))
					.withMessage(new Message()
							.withBody(new Body().withText(new Content().withCharset("UTF-8").withData(textBody)))
							.withSubject(new Content().withCharset("UTF-8").withData(subject)))
					.withSource(FROM);
			client.sendEmail(req);
			kStreamService.streamLogs(Constants.EMAIL_SENT);
		} catch (Exception e) {
			kStreamService.streamLogs(e.getMessage());
		}
	}
}
