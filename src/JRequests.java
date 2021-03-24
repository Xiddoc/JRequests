import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class JRequests {
	public static String get(String url) throws IOException {
		// Init URL
		URL requestURL = new URL(url);

		// Init connection
		HttpURLConnection con = (HttpURLConnection) requestURL.openConnection();

		// Set method
		con.setRequestMethod("GET");

		// Set headers
//		con.setRequestProperty("Content-Type", "application/json");

		// Set params
//		Map<String, String> parameters = new HashMap<>();
//		parameters.put("param1", "val");
//
//		con.setDoOutput(true);
//		DataOutputStream out = new DataOutputStream(con.getOutputStream());
//		out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
//		out.flush();
//		out.close();

		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);

		return FullResponseBuilder.getFullResponse(con);
	}
}

