import com.sun.deploy.net.HttpResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class JRequest {
	// For keyword arguments
	private final String method;
	private final String url;
	private final HashMap<String, String> headers;
	private final HashMap<String, String> data;
	private final HashMap<String, String> params;
	private final HashMap<String, String> cookies;

	public JRequest(String method,
					String url,
					HashMap<String, String> headers,
					HashMap<String, String> data,
					HashMap<String, String> params,
					HashMap<String, String> cookies) {

		this.method = method;
		this.url = url;
		this.headers = headers;
		this.data = data;
		this.params = params;
		this.cookies = cookies;
	}

	public JResponse send() throws IOException {
		// Initialize the HTTP connection to a URL
		HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();

		// Set the method
		con.setRequestMethod(this.method());

		// Set headers
		if (this.headers() != null) {
			// For each header
			this.headers().forEach(con::setRequestProperty);
		}

//		con.setDoOutput(true);
//		DataOutputStream out = new DataOutputStream(con.getOutputStream());
//		out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
//		out.flush();
//		out.close();

		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);

		return new JResponse(this, con);
	}

	private static String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
	}

	public String method() {
		return method;
	}

	public String url() {
		return url;
	}

	public HashMap<String, String> headers() {
		return headers;
	}

	public HashMap<String, String> data() {
		return data;
	}

	public HashMap<String, String> params() {
		return params;
	}

	public HashMap<String, String> cookies() {
		return cookies;
	}

	public String toString() {
		return "<Request [" + this.method() + "]>";
	}
}
