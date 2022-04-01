package serverless;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Test implements RequestHandler<Void, Map<String, Object>> {

	public Map<String, Object> handleRequest(Void input, Context context) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("statusCode", 200);
		response.put("body", "registered");
		return response;
	}

}
