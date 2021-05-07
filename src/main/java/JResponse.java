
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;

public class JResponse {
	private final int statusCode;
	private final String reason;
	private final HashMap<String, List<String>> headers;
	private final String content;
	private JRequest request;

	public JResponse(JRequest originalRequest, HttpURLConnection connection) throws IOException {
		// Set request
		this.request = originalRequest;

		// Set status code
		this.statusCode = connection.getResponseCode();

		// Set status message
		this.reason = connection.getResponseMessage();

		// Read the headers
		this.headers = new HashMap<>(connection.getHeaderFields());
		// Remove null header (HTTP/1.0 200 OK)
		this.headers.remove(null);

		// Choose which reader to use by status code
		Reader streamReader;

		// If the request failed (error)
		if (!this.isSuccess()) {
			// Then use the error stream reader
			streamReader = new InputStreamReader(connection.getErrorStream());
		} else {
			// Otherwise, use the default stream reader
			streamReader = new InputStreamReader(connection.getInputStream());
		}

		// Get stream as buffer
		BufferedReader bufferedReader = new BufferedReader(
			streamReader
		);

		// Start reading
		String inputLine;
		StringBuilder content = new StringBuilder();
		// Keep reading
		while (true) {
			// Read the next line
			inputLine = bufferedReader.readLine();
			// If it is not null (end of stream)
			if (inputLine != null) {
				// Then append it to the content
				content.append(inputLine);
			} else {
				// Otherwise, exit the loop
				break;
			}
		}

		// Close the buffer as we don't need to read any more data
		bufferedReader.close();

		// Disconnect connection after reading stream is over
		connection.disconnect();

		// Set the content
		this.content = content.toString();
	}

	public boolean isSuccess() {
		return this.statusCode() < 300;
	}

	public int statusCode() {
		return statusCode;
	}

	public String reason() {
		return reason;
	}

	public String content() {
		return content;
	}

	public HashMap<String, List<String>> headers() {
		return this.headers;
	}

	public JRequest request() {
		return this.request;
	}
}
