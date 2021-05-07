import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class JRequests {
	public static JResponse get(String url) throws IOException {
		return request("GET", url);
	}

	public static JResponse post(String url) throws IOException {
		return request("POST", url);
	}

	public static JResponse request(String method, String url) throws IOException {
		return request(method, url, null, null);
	}

	public static JResponse request(String method, String url, HashMap<String, String> params) throws IOException {
		return request(method, url, params, null);
	}

	public static JResponse request(String method,
									String url,
									HashMap<String, String> params,
									HashMap<String, String> headers) throws IOException {
		// Send all info to a JRequest object, then execute the request.
		return new JRequest(method, url, headers, params, params, params).send();
	}

}

