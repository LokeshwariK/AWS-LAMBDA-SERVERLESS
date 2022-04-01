package serverless;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * This class implement the RequestHandler to handle request from aws lambda function.
 * @author kiran
 *
 */
public class Handler implements RequestHandler<Map<String, Object>, Map<String, Object>> {
	
	public static final String THIRD_PARTY_API = "https://webhook.site/e56e3d46-5ad3-4c10-b339-3e71300afd23";
	
	/**
	 * This method is to handle the request and return the response.
	 */
	public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
		Map<String, Object> response = new HashMap<String, Object>();

		LambdaLogger logger = context.getLogger();

		logger.log("REQUESTED API : https://fx3r6hu6t9.execute-api.ap-south-1.amazonaws.com/dev/webhooks");

		String body = (String) input.get("body");
		
		JSONObject json = new JSONObject(body);
		
		String username = json.get("username").toString();
		String password = json.get("password").toString();

		try {
			Object webhook_response = getResponseFrom(THIRD_PARTY_API, username,
					password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.put("statusCode", 200);
		response.put("body", input.toString());
		return response;
	}
	
	/**
	 * This method is to provide connection between the url and return the user info.
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public String getResponseFrom(String url, String username, String password) throws Exception {
		URL object = new URL(url);

		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("POST");

		JSONObject cred = new JSONObject();

		cred.put("username", username);
		cred.put("password", password);

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(cred.toString());
		wr.flush();

		return con.getContent().toString();
	}

}
